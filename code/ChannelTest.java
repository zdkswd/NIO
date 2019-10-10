package NIO;

import javafx.scene.shape.Path;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author zdkswd
 * @date 2019/10/10 14:14
 */
public class ChannelTest {
    public static void main(String[] args) throws IOException {
        //getChannel();
        open();
    }
    private static void getChannel() throws IOException {
        //1.利用通道完成文件的复制
        FileInputStream inputStream = new FileInputStream("C:\\Users\\86199\\IdeaProjects\\NIO\\src\\NIO\\1.jpg");
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\86199\\IdeaProjects\\NIO\\src\\NIO\\2.jpg");

        //获得通道
        FileChannel inputStreamChannel = inputStream.getChannel();
        FileChannel outputStreamChannel = outputStream.getChannel();

        //分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //将通道中的数据存入缓冲区中
        while (inputStreamChannel.read(buffer)!=-1){
            buffer.flip();//切换成读数据的模式
            //将缓冲区的数据写入通道
            outputStreamChannel.write(buffer);
            buffer.clear();//清空缓冲区
        }

        outputStreamChannel.close();
        inputStreamChannel.close();
        outputStream.close();
        inputStream.close();
    }

    //使用直接缓冲区完成文件的复制（内存映射文件）
    private static void open() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("C:\\Users\\86199\\IdeaProjects\\NIO\\src\\NIO\\1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("C:\\Users\\86199\\IdeaProjects\\NIO\\src\\NIO\\3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);

        //内存映射文件
        MappedByteBuffer inMappedByteBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedByteBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        //直接对缓冲区进行数据的读写操作
        byte[] dst=new byte[inMappedByteBuffer.limit()];
        inMappedByteBuffer.get(dst);
        outMappedByteBuffer.put(dst);

        inChannel.close();
        outChannel.close();
    }
}
