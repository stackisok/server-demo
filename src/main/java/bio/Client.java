package bio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/***
 *
 *@Author ChenjunWang
 *@Description:
 *@Date: Created in 18:11 2020/2/12
 *@Modified By:
 *
 */
public class Client {


    public static void main(String[] args) {
        start();
    }

    private static void start() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(Server.ip, Server.port));

            Thread.sleep(1000);
            socket.getOutputStream().write("hello world".getBytes());


            byte[] buffer = new  byte[1024];
            while (socket.getInputStream().read(buffer) > 0) {
                System.out.println(new String(buffer));
                buffer = new  byte[1024];
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
