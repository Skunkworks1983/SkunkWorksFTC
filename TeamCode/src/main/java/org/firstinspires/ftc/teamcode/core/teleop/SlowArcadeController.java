package org.firstinspires.ftc.teamcode.core.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Adam.
 * October 17, 2016 at 7:56 PM
 */
@TeleOp(name="Slow Arcade Drive", group="Linear Opmode")
public class SlowArcadeController extends OpMode
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


        float xValue = gamepad1.left_stick_x*0.5f;
        float yValue = -gamepad1.left_stick_y *0.5f;


        float leftPower = yValue + xValue;
        float rightPower = yValue - xValue;


        leftPower = Range.clip(leftPower, -1, 1);
        rightPower = Range.clip(rightPower, -1, 1);


        leftMotor1.setPower(leftPower);
        rightMotor1.setPower(rightPower);
        leftMotor2.setPower(leftPower);
        rightMotor2.setPower(rightPower);



    }

}

