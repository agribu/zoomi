package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import zoom.Controller;

public class Handler extends Thread {

    private String name;
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Controller c = null;
    
    public Handler(Socket socket) {
        this.socket = socket;
        this.init();
    }
    
    private void init() {
        this.c = new Controller();
        c.setName("Controller: Motor");
    }

    @Override
    public void run() {
        int num = 0;
        String input = "";
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Starting Thread: " + c.getName());
            c.start();

            while (true) {
                input = in.readLine();

                if (!input.equals("")) {
                    try {
                        num = Integer.valueOf(input);
                    } catch (NumberFormatException e) {
            
                    }
                }
                System.out.println(input + "\t num: " + num);
                this.handleInput(num);
                
            }
        } catch (IOException ex) {
            System.out.println("Client disconnected unexpectedly:\n" + ex.getMessage());
            c.shutdown();
        } catch (NullPointerException ex) {
            System.out.println("Client disconnect");
            c.shutdown();
        }
    }

    public void handleInput(int i) {
        if (i == 0) {
            c.setNextAction("stop"); // Stop
        } else if (i > 0 && i < 10) {
            c.setNextAction("sfwd"); // Slow forward
        } else if (i >= 10 && i <= 20) {
            c.setNextAction("ffwd"); // Fast foward
        } else if (i < 0 && i > -10) {
            c.setNextAction("sbwd"); // Slow backward
        } else if (i <= -10 && i >= -20) {
            c.setNextAction("fbwd"); // Fast backward
        }
    }
}
