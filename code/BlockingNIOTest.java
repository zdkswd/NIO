package NIO;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author zdkswd
 * @date 2019/10/10 19:12
 */
public class BlockingNIOTest {
    public static void main(String[] args) throws IOException {
        server();
        client();
    }

    private static void client() throws IOException {
        //获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 2333));
        FileChannel fileChannel = FileChannel.open(Paths.get("C:\\Users\\86199\\IdeaProjects\\NIO\\src\\NIO\\1.txt"), StandardOpenOption.READ);

        //分配指定大小的缓冲区域
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //读取本地文件并发送到服务端去。
        while (fileChannel.read(byteBuffer)!=-1){
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        //关闭通道
        fileChannel.close();
        socketChannel.close();
    }
    private static void server() throws IOException {
        //获得通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        FileChannel fileChannel = FileChannel.open(Paths.get("C:\\Users\\86199\\IdeaProjects\\NIO\\src\\NIO\\4.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //绑定连接
        serverSocketChannel.bind(new InetSocketAddress(2333));

        //获取客户端连接通道
        SocketChannel socketChannel = serverSocketChannel.accept();

        //分配一个指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //接收客户端的数据并保存到服务端
        while (socketChannel.read(byteBuffer)!=-1){
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        //关闭通道
        socketChannel.close();
        serverSocketChannel.close();
        fileChannel.close();
    }
}
