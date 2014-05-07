package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private int port;

    public Server(int port) {
        this.port = port;
        
        System.out.println("Server running.");
        this.start();
    }

    private void start() {
        try (ServerSocket listener = new ServerSocket(port)) {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
