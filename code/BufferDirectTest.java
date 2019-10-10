package NIO;

import java.nio.ByteBuffer;

/**
 * @author zdkswd
 * @date 2019/10/10 13:55
 */
public class BufferDirectTest {
    public static void main(String[] args) {
        //分配直接缓冲区
        ByteBuffer direct = ByteBuffer.allocateDirect(1024);
        System.out.println(direct.isDirect());
    }
}
