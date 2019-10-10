package NIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zdkswd
 * @date 2019/10/10 16:19
 */
public class ScatterGatherTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("C:\\Users\\86199\\IdeaProjects\\NIO\\src\\NIO\\1.txt", "rw");

        //获取通道
        FileChannel channel = accessFile.getChannel();

        //分配指定大小的缓冲区
        ByteBuffer buffer1 = ByteBuffer.allocate(100);
        ByteBuffer buffer2 = ByteBuffer.allocate(10240);

        //分散读取
        ByteBuffer[] buffers={buffer1,buffer2};
        channel.read(buffers);

        for (ByteBuffer buffer : buffers) {
            buffer.flip();
        }

        System.out.println(new String(buffers[0].array(),0,buffers[0].limit()));
        System.out.println("====================");
        System.out.println(new String(buffers[1].array(),0,buffers[1].limit()));

        //聚集写入
        RandomAccessFile rw = new RandomAccessFile("C:\\Users\\86199\\IdeaProjects\\NIO\\src\\NIO\\2.txt", "rw");
        FileChannel channel2 = rw.getChannel();

        channel2.write(buffers);
    }
}
