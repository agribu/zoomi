package zoom;

/**
 *
 * @author agribu
 */
public class StepSequencer {

    // @see http://www.lirtex.com/robotics/stepper-motor-controller-circuit/
    // for additional details on stepping techniques
    // create byte array to demonstrate a single-step sequencing
    // (This is the most basic method, turning on a single electromagnet every time.
    //  This sequence requires the least amount of energy and generates the smoothest movement.)
    private final byte[] single_step_sequence = {
        (byte) 0b0001,
        (byte) 0b0010,
        (byte) 0b0100,
        (byte) 0b1000};

    // create byte array to demonstrate a double-step sequencing
    // (In this method two coils are turned on simultaneously.  This method does not generate 
    //  a smooth movement as the previous method, and it requires double the current, but as 
    //  return it generates double the torque.)
    private final byte[] double_step_sequence = {
        (byte) 0b0011,
        (byte) 0b0110,
        (byte) 0b1100,
        (byte) 0b1001};

    // create byte array to demonstrate a half-step sequencing
    // (In this method two coils are turned on simultaneously.  This method does not generate 
    //  a smooth movement as the previous method, and it requires double the current, but as 
    //  return it generates double the torque.)
    private final byte[] half_step_sequence = {
        (byte) 0b0001,
        (byte) 0b0011,
        (byte) 0b0010,
        (byte) 0b0110,
        (byte) 0b0100,
        (byte) 0b1100,
        (byte) 0b1000,
        (byte) 0b1001};

    // There are 32 steps per revolution on my sample motor, and inside is a ~1/64 reduction gear set.
    // Gear reduction is actually: (32/9)/(22/11)x(26/9)x(31/10)=63.683950617
    // This means is that there are really 32*63.683950617 steps per revolution =  2037.88641975 ~ 2038 steps!         
    private final int defaultStepsPerRevolution = 2038;
    
    

    /**
     * Creates a new Step Sequencer Object
     */
    public StepSequencer() {

    }

    /**
     * Returns a byte array for single step sequencing
     *
     * @return byte array for single step sequencing
     */
    public byte[] getSingleStep() {
        return this.single_step_sequence;
    }

    /**
     * Returns a byte array for double step sequencing
     *
     * @return byte array for double step sequencing
     */
    public byte[] getDoubleStep() {
        return this.double_step_sequence;
    }

    /**
     * Returns a byte array for half step sequencing
     *
     * @return byte array for half step sequencing
     */
    public byte[] getHalfStep() {
        return this.half_step_sequence;
    }

    /**
     * Returns the default value of steps per revolution
     *
     * @return steps per revolution
     */
    public int getDefStepsPerRev() {
        return this.defaultStepsPerRevolution;
    }
    
    /**
     * Returns number of motor steps for given degree value
     * 
     * @param degree Degree value which is to be converted in number of motor steps
     * @return Number of motor steps, to rotate by a given degree
     */
    public int getDegreeToSteps(int degree) {
        // Determine number of steps for one degree of rotation
        int stepsPerDegree = this.getDefStepsPerRev() / 360;
        
        return stepsPerDegree * degree;
    }
}
