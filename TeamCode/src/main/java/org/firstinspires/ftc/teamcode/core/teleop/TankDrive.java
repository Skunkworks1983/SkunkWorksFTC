package org.firstinspires.ftc.teamcode.core.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Adam.
 * October 26, 2016 at 7:03 PM
 */

@TeleOp(name="Tank Drive", group="Linear Opmode")
public class TankDrive extends OpMode
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
        // Left stick is power for left side, etc
        float leftPower = gamepad1.left_stick_y;
        float rightPower = gamepad1.right_stick_y;

        //Clip the power values so that it only goes from -1 to 1
        leftPower = Range.clip(leftPower, -1, 1);
        rightPower = Range.clip(rightPower, -1, 1);

        //Set the power of the motors with the gamepad values
        leftMotor1.setPower(leftPower);
        leftMotor2.setPower(leftPower);
        rightMotor1.setPower(rightPower);
        rightMotor2.setPower(rightPower);
    }
}
