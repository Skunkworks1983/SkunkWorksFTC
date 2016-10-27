package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 Created by Adam.
 October 12, 2016 at 8:03 PM
 */
// Build on robot phone

@Autonomous(name="Drive Forward", group="Drive Forward")
public class DriveForward extends LinearOpMode
{
    DcMotor leftMotor1;
    DcMotor rightMotor1;
    DcMotor leftMotor2;
    DcMotor rightMotor2;

    @Override
    public void runOpMode() throws InterruptedException {
        //setup the left and right motors from the configuration file
        leftMotor1 = hardwareMap.dcMotor.get("left1");
        rightMotor1 = hardwareMap.dcMotor.get("right1");
        leftMotor2 = hardwareMap.dcMotor.get("left2");
        rightMotor2 = hardwareMap.dcMotor.get("right2");

        waitForStart();

        //Stop the robot
        leftMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor2.setDirection(DcMotorSimple.Direction.REVERSE);

        leftMotor1.setPower(0.25);
        rightMotor1.setPower(0.25);
        leftMotor2.setPower(0.25);
        rightMotor2.setPower(0.25);
        sleep(6000);
    }
}
