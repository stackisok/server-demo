package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/***
 *
 *@Author ChenjunWang
 *@Description:
 *@Date: Created in 18:31 2020/2/14
 *@Modified By:
 *
 */
public class Client {
    protected static final String ip = "127.0.0.1";
    protected static final int port = 6010;

    public static void main(String[] args) {
        start();
    }

    private static void start() {

        try {
            SocketChannel sc = SocketChannel.open();

            sc.connect(new InetSocketAddress(ip, port));//建立连接

            ByteBuffer readBuffer = ByteBuffer.allocate(1024);//缓冲区
            ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
            sendBuffer.put("hello".getBytes());//将要发送的数据写入buffer

            sc.write(sendBuffer);//发送消息
            int length = sc.read(readBuffer);//读取信息
            String s = new String(readBuffer.array(), 0, length);
            System.out.println(s);

            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
