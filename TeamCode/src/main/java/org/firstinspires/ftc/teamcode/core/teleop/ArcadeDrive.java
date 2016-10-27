package org.firstinspires.ftc.teamcode.core.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Adam.
 * October 17, 2016 at 7:56 PM
 */
@TeleOp(name="Arcade Drive", group="Linear Opmode")
public class ArcadeDrive extends OpMode
{
//    final double LEFT_OPEN_POSITION = 0.0;
//    final double LEFT_CLOSED_POSITION = 0.5;
//    final double RIGHT_OPEN_POSITION = 1.0;
//    final double RIGHT_CLOSED_POSITION = 0.5;

    DcMotor leftMotor1;
    DcMotor rightMotor1;
    DcMotor leftMotor2;
    DcMotor rightMotor2;
//    Servo leftGripper;
//    Servo rightGripper;
//    DcMotor leftArm;

    @Override
    public void init()
    {
        //Get references to the motors and servos from the hardware map
//        leftGripper = hardwareMap.servo.get("left_hand");
//        rightGripper = hardwareMap.servo.get("right_hand");
//        leftArm = hardwareMap.dcMotor.get("left_arm");
        leftMotor1 = hardwareMap.dcMotor.get("left_drive1");
        rightMotor1 = hardwareMap.dcMotor.get("right_drive1");
        leftMotor2 = hardwareMap.dcMotor.get("left_drive2");
        rightMotor2 = hardwareMap.dcMotor.get("right_drive2");
        //Reverse the right motor
        rightMotor1.setDirection(DcMotor.Direction.REVERSE);
        rightMotor2.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop()
    {

        //Get the values from the gamepads
        //Note: pushing the stick all the way up returns -1,
        // so we need to reverse the y values
        float leftPower = gamepad1.left_stick_x;
        float rightPower = -gamepad1.left_stick_y;

        //Clip the power values so that it only goes from -1 to 1
        leftPower = Range.clip(leftPower, -1, 1);
        rightPower = Range.clip(rightPower, -1, 1);

        //Set the power of the motors with the gamepad values
        leftMotor1.setPower(leftPower);
        rightMotor1.setPower(rightPower);
        leftMotor2.setPower(leftPower);
        rightMotor2.setPower(rightPower);

        // This code will control the up and down movement of
        // the arm using the y and b gamepad buttons.
//        if(gamepad1.y) {
//            leftArm.setPower(0.1);
//        }
//        else if(gamepad1.b) {
//            leftArm.setPower(-0.1);
//        }
//        else {
//            leftArm.setPower(0);
//        }

        // This code will open and close the gripper with two buttons
        // using 1 button to open and another to close the gripper
//        if(gamepad1.x) {
//            leftGripper.setPosition(LEFT_OPEN_POSITION);
//            rightGripper.setPosition(RIGHT_OPEN_POSITION);
//        }
//        if(gamepad1.a) {
//            leftGripper.setPosition(LEFT_CLOSED_POSITION);
//            rightGripper.setPosition(RIGHT_CLOSED_POSITION);
//        }

    }

}

