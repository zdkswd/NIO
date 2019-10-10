package NIO;

import java.nio.ByteBuffer;

/**
 * @author zdkswd
 * @date 2019/10/10 13:01
 */
public class BufferTest {
    public static void main(String[] args) {
        String str="abcd";
        //1.分配一个指定大小的数组
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        //2.利用put存入数据到缓冲区中
        buffer.put(str.getBytes());
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        //3.切换读取数据模式
        buffer.flip();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        //4.利用get()读取缓冲区中的数据
        byte[] dst=new byte[buffer.limit()];
        buffer.get(dst);
        System.out.println(new String(dst,0,dst.length));
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        //5.rewind()
        buffer.rewind();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        //6.clear()
        buffer.clear();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

    }
}
