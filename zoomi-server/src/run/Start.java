package run;

import network.Server;
import com.pi4j.io.gpio.exception.GpioPinExistsException;

/**
 *
 * @author agribu
 */
public class Start {

    public static void main(String[] args) {
        try {
            Server s = new Server(9001);
        } catch (GpioPinExistsException e) {
            System.out.println(e.getMessage() + "\tPin:" + e.getPin());
        }
            
    }
}
