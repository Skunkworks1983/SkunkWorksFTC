package org.firstinspires.ftc.teamcode.core.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

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
    int power;
    int location;//find more efficient way later - Adam
    int[] powerTypes;

    /*
        Shift up and down with power
     */

    @Override
    public void init()
    {
        leftMotor1 = hardwareMap.dcMotor.get("left_drive1");
        rightMotor1 = hardwareMap.dcMotor.get("right_drive1");
        leftMotor2 = hardwareMap.dcMotor.get("left_drive2");
        rightMotor2 = hardwareMap.dcMotor.get("right_drive2");
        power = 100;
        powerTypes = new int[]{100, 75, 50, 25, 15, 10, 5};
        location = 0;


        //Reverse the right motor
        rightMotor1.setDirection(DcMotor.Direction.REVERSE);
        rightMotor2.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop()
    {
        // Toggle Slow Mode
        if(gamepad1.a)
        {
            if(location == powerTypes.length)
                location = 0;
            else
                power = powerTypes[++location];
        }

        telemetry.addData("Power", power + "%");
        telemetry.update();

        // Left stick is power for left side, etc
        float leftPower = -gamepad1.left_stick_y * power;
        float rightPower = -gamepad1.right_stick_y * power;

        //Set the power of the motors with the gamepad values
        leftMotor1.setPower(leftPower);
        leftMotor2.setPower(leftPower);
        rightMotor1.setPower(rightPower);
        rightMotor2.setPower(rightPower);
    }
}
