package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/***
 *
 *@Author ChenjunWang
 *@Description:
 *@Date: Created in 17:48 2020/2/13
 *@Modified By:
 *
 */
public class Server {

    protected static final String ip = "127.0.0.1";
    protected static final int port = 6010;

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();

            ssc.bind(new InetSocketAddress(ip, port));

            ssc.configureBlocking(false);
            Selector selector = Selector.open();

            ssc.register(selector, SelectionKey.OP_ACCEPT);

            while(true) {
                selector.select(); //阻塞方法 若返回则说明有 感兴趣的事件准备好了
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = keys.iterator();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
                while (keyIterator.hasNext()) {//处理客户端连接
                    SelectionKey key = keyIterator.next();


                    if (key.isAcceptable()) {

                        ssc = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = ssc.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println("有客户端连接!");

                    }
                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        readBuffer.clear();//清除缓冲区
                        int numRead;
                        try{
                            numRead = socketChannel.read(readBuffer);
                            if (numRead == -1) {
                                socketChannel.close();
                                key.cancel();
                                continue;
                            }
                        }catch (IOException e){
                            key.cancel();
                            socketChannel.close();
                            continue;
                        }
                        String msg = new String(readBuffer.array(),0,numRead);
                        System.out.println("接收到消息" + msg);
                        socketChannel.register(selector,SelectionKey.OP_WRITE);


                    }
                    if (key.isWritable()) {
                        String msg = "hello";
                        SocketChannel channel = (SocketChannel) key.channel();
                        System.out.println("发送消息:" + msg);

                        sendBuffer.clear();
                        sendBuffer.put(msg.getBytes());
                        sendBuffer.flip();//由写变为读，反转
                        channel.write(sendBuffer);
                        channel.register(selector,SelectionKey.OP_READ); //注册读操作

                    }
                    keyIterator.remove(); //移除当前的key
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
