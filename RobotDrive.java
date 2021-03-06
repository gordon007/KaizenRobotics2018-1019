package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.robot.Robot;
import java.util.Set;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/*
 * Robot drive class, this is the class that actually operates the teleOP portion of the code.
 */
@TeleOp(name="Robot Drive", group="Iterative Opmode")
public class RobotDrive extends OpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    public KaizenRobot robot = null;
    
    //detoes whether or not the claw is in the proccess of applying closing pressure
    boolean isHeld;
    
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        robot = new KaizenRobot(hardwareMap);
        isHeld = false;
        
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        
        //Set power of Motors with joysticks
        robot.setRightVelocity(-gamepad1.right_stick_y);
        robot.setLeftVelocity(-gamepad1.left_stick_y);
        
        //Turn the Robot to the side if dpad is pressed
        //Turn Left if Left, Right if Right
        if(gamepad1.dpad_right)
            robot.turn(50.0);
        else if(gamepad1.dpad_left)
            robot.turn(-50.0);
        else
            robot.turn(0.0);
        
        //Hold Claw until it is oppened with the right trigger
        //Move Claw if it is not Closed
        
        //if left trigger open, else stay if not closing
        if(gamepad1.left_trigger > 0)
        {
            robot.setClawPosition(0);
            isHeld = false;
        }
        else if(!isHeld)
        {
        robot.setClawPosition(.5);
        }
        
        //if right trigger close
        if(gamepad1.right_trigger > 0)
        {
            robot.setClawPosition(1);
            isHeld = true;
        }
        
        //Move base of claw on player input
        //Down with X, up with Y
        if(gamepad1.y)
            robot.setBaseVelocity(.1); //Change this to be more or less powerful based on need
        else if(gamepad1.x)
            robot.setBaseVelocity(-.1); //Change this to be more or less powerful based on need
        else
            robot.setBaseVelocity(0.0);
            
        //Move joint of claw on player dPad input
        //Down with down Up with up
        if(gamepad1.dpad_up)
            robot.setJointVelocity(.1);
        else if(gamepad1.dpad_down)
            robot.setJointVelocity(-1.0); //Modify to make more or less powerful based on need- Down on Dpad
        else
            robot.setJointVelocity(0.0);
            
        //Show time elapsed
        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }

        
    @Override
    public void stop() {
    }

} 
