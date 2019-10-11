package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;

/**
 * @author zdkswd
 * @date 2019/10/11 10:16
 */
public class NonBlockingNIOTest {
    public static void main(String[] args) {

    }

    private static void client() throws IOException {
        //1.获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        //2.切换成非阻塞模式
        socketChannel.configureBlocking(false);

        //3.分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //4.发送数据给服务端
        byteBuffer.put(new Date().toString().getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        byteBuffer.clear();

        //5.关闭通道
        socketChannel.close();

    }

    private static void server() throws IOException {
        //1.获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //2.切换到非阻塞模式
        serverSocketChannel.configureBlocking(false);

        //3.绑定连接
        serverSocketChannel.bind(new InetSocketAddress(9898));

        //4.获取选择器
        Selector selector = Selector.open();

        //5.将通道注册到选择器上,此时不用再使用accept()阻塞等待了
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //6.轮询式的获取选择器上已经就绪的事件
        while (selector.select() > 0) {
            //7.获得当前选择器中所有注册的“选择键（已就绪的监听事件）”
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                //8.获取准备就绪的事件
                SelectionKey next = iterator.next();

                //9.判断具体是什么事件准备就绪
                if (next.isAcceptable()) {
                    //10.若接收就绪，获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    //11.切换非阻塞模式
                    socketChannel.configureBlocking(false);

                    //12.将该通道注册到选择器上
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (next.isReadable()) {
                    //13.获取当前选择器上读就绪状态的通道
                    SocketChannel channel = (SocketChannel) next.channel();

                    //14.读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    int len = 0;
                    while ((len = channel.read(buffer)) > 0) {
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, len));
                        buffer.clear();
                    }
                }
                iterator.remove();
            }
        }


    }
}
