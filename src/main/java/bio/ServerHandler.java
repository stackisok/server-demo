package bio;

import java.io.IOException;
import java.net.Socket;

/***
 *
 *@Author ChenjunWang
 *@Description:
 *@Date: Created in 21:42 2020/2/12
 *@Modified By:
 *
 */
public class ServerHandler implements Runnable {
    private Socket client;

    public ServerHandler(Socket client) {
        this.client = client;
    }

    public void run() {
        byte[] buffer = new  byte[1024];
        while (true) {
            int read = 0;
            try {
                read = client.getInputStream().read(buffer);
                if (!(read > 0)) break;
                System.out.println(new String(buffer,0, read));
                client.getOutputStream().write(buffer,0,read);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }


    }
}
