package NIO;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @author zdkswd
 * @date 2019/10/10 17:48
 */
public class CharsetTest {
    public static void main(String[] args) throws CharacterCodingException {
        Charset charset = Charset.forName("GBK");
        //获取编码器
        CharsetEncoder encoder = charset.newEncoder();

        //获取解码器
        CharsetDecoder decoder = charset.newDecoder();

        CharBuffer buffer = CharBuffer.allocate(1024);
        buffer.put("lala啊");
        buffer.flip();

        //编码
        ByteBuffer byteBuffer = encoder.encode(buffer);

        //解码
        CharBuffer charBuffer = decoder.decode(byteBuffer);
        System.out.println(charBuffer.toString());
    }
}
