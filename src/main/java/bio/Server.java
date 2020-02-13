package bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/***
 *
 *@Author ChenjunWang
 *@Description:
 *@Date: Created in 18:02 2020/2/12
 *@Modified By:
 *
 */
public class Server {

    protected static final String ip = "127.0.0.1";
    protected static final int port = 6010;

    private static void start() {
        try {
            ServerSocket server = new ServerSocket(port);

            while(true) {
                Socket client = server.accept();
                System.out.println("accept");
                new Thread(new ServerHandler(client)).start();

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        start();
    }


}
