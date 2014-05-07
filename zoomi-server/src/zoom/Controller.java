/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoom;

/**
 *
 * @author agribu
 */
public class Controller extends Thread {

    private final Motor motor;
    private String cAction; //current Action
    private String nAction; //next Action

    public Controller() {
        this.motor = new Motor();
        this.cAction  = "stop";
        this.nAction = "";
    }
    
    @Override
    public void run() {
        while (true) {
            if (!nAction.equals(cAction)) {
                // Stop before performing next action
                this.stopAction();
                this.performAction();
            } else {
                this.performAction();
            }
        }
    }
    
    private void ffwdAction() {
        this.cAction = "ffwd";
        motor.setActivity(true);
        motor.rotateFast(1);
    }
    
    private void fbwdAction() {
        this.cAction = "fbwd";
        motor.setActivity(true);
        motor.rotateFast(-1);
    }
    
    private void sfwdAction() {
        this.cAction = "sfwd";
        motor.setActivity(true); 
        motor.rotateSlow(1);
    }
    
    private void sbwdAction() {
        this.cAction = "sbwd";
        motor.setActivity(true);
        motor.rotateSlow(-1);
    }
    
    public void stopAction() {
        this.cAction = "stop";
        motor.setActivity(false);
        motor.rotateStop();
    }
    
    public void setNextAction(String s) {
        this.nAction = s;
    }
    
    private void performAction() {
        switch (this.nAction) {
            case "ffwd":
                this.ffwdAction();
                break;
            case "fbwd":
                this.fbwdAction();
                break;
            case "sfwd":
                this.sfwdAction();
                break;
            case "sbwd":
                this.sbwdAction();
                break;
            default:
                this.stopAction();
        }
    }
    
    public void shutdown() {
        motor.shutdownGPIO();
    }


}
