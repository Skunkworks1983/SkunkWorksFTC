package org.firstinspires.ftc.teamcode.core.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.core.CustomOpMode;

/**
 * Created by Adam.
 * October 26, 2016 at 7:03 PM
 */

@TeleOp(name="Tank Drive", group="Drive")
public class TankDrive extends CustomOpMode
{
    DcMotor leftMotor1;
    DcMotor rightMotor1;
    DcMotor leftMotor2;
    DcMotor rightMotor2;

    @Override
    public void init()
    {
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
        // Check for powering up or down

        powerUpDown();

        // Left stick is power for left side, etc
        float leftPower = -gamepad1.left_stick_y * getPower();
        float rightPower = -gamepad1.right_stick_y * getPower();

        //Set the power of the motors with the gamepad values
        leftMotor1.setPower(leftPower);
        leftMotor2.setPower(leftPower);
        rightMotor1.setPower(rightPower);
        rightMotor2.setPower(rightPower);
    }
}
