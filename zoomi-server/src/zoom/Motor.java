package zoom;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 *
 * @author agribu
 */
public class Motor {

    // gpio controller
    private final GpioController gpio = GpioFactory.getInstance();

    // provide gpio output pins #0 to #3 in LOW state
    private final GpioPinDigitalOutput[] pins = {
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW),
        gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW)};

    // motor component
    private GpioStepperMotorComponent motor;

    // step sequencer
    private final StepSequencer seq;

    private boolean isActive;

    public Motor() {
        // ensure, that the motor ist stopped, when the program terminates
        gpio.setShutdownOptions(true, PinState.LOW, this.pins);


        // initialize motor component
        this.motor = new GpioStepperMotorComponent(this.pins);

        // create new step sequencer
        this.seq = new StepSequencer();
    }

    /**
     * Rotates step motor using single step method, 2ms intervals and a complete
     * rotation.
     *
     * @param direction Direction of rotation: -1 negative rotation, 1 positive
     * rotation
     */
    public void rotateFast(int direction) {
        motor.setStepInterval(2);
        motor.setStepSequence(this.seq.getSingleStep());
        this.rotateStart(direction);
    }

    /**
     * Rotates step motor using double step method, 2ms intervals and a complete
     * rotation.
     *
     * @param direction Direction of rotation: -1 negative rotation, 1 positive
     * rotation
     */
    public void rotateSlow(int direction) {
        motor.setStepInterval(2);
        motor.setStepSequence(this.seq.getHalfStep());
        this.rotateStart(direction);
    }

    private void rotateStart(int direction) {
        if (isActive) {
            if (direction == 1) {
                motor.forward();
            } else if (direction == -1) {
                motor.reverse();
            }
        }
    }

    /**
     * Final stop to ensure no motor activity.
     */
    public void rotateStop() {
        motor.stop();
    }

    /**
     * Stop all GPIO activity/threads by shutting down the GPIO controller. This
     * method will forcefully shutdown all GPIO monitoring threads and scheduled
     * tasks.
     */
    public void shutdownGPIO() {
        this.rotateStop();
        gpio.shutdown();
        gpio.unexportAll();
        gpio.unprovisionPin(pins);
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActivity(boolean isActive) {
        this.isActive = isActive;
    }

    public void refreshGPIO() {
        this.shutdownGPIO();

        for (GpioPinDigitalOutput g : pins) {
            g.low();
        }

        // ensure, that the motor ist stopped, when the program terminates
        gpio.setShutdownOptions(true, PinState.LOW, this.pins);

        // initialize motor component
        this.motor = new GpioStepperMotorComponent(this.pins);
    }
}
