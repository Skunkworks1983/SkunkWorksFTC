package org.firstinspires.ftc.teamcode.core.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Adam.
 * November 07, 2016 at 7:07 PM
 */

public class MotorsHardware
{
    public DcMotor leftMotor1 = null;
    public DcMotor leftMotor2 = null;
    public DcMotor rightMotor1 = null;
    public DcMotor rightMotor2 = null;

    /* local OpMode members. */
    HardwareMap hwMap =  null;

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap)
    {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftMotor1 = hwMap.dcMotor.get("leftDrive1");
        leftMotor2 = hwMap.dcMotor.get("leftDrive2");
        rightMotor1  = hwMap.dcMotor.get("rightDrive1");
        rightMotor2  = hwMap.dcMotor.get("rightDrive2");
        rightMotor1.setDirection(DcMotor.Direction.REVERSE);
        rightMotor2.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        setLeftPower(0);
        setRightPower(0);
    }

    public boolean isInit()
    {
        return hwMap != null;
    }

    public void setLeftPower(double power)
    {
        leftMotor1.setPower(power);
        leftMotor2.setPower(power);
    }

    public void setRightPower(double power)
    {
        rightMotor1.setPower(power);
        rightMotor2.setPower(power);
    }

    public void resetLeftPower()
    {
        leftMotor1.setPower(0);
        leftMotor2.setPower(0);
        leftMotor1.setDirection(DcMotorSimple.Direction.FORWARD);
        leftMotor2.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void resetRightPower()
    {
        rightMotor1.setPower(0);
        rightMotor2.setPower(0);
        rightMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setLeftMode(DcMotor.RunMode mode)
    {
        leftMotor1.setMode(mode);
        leftMotor2.setMode(mode);
    }

    public void setRightMode(DcMotor.RunMode mode)
    {
        rightMotor1.setMode(mode);
        rightMotor2.setMode(mode);
    }

    public void setLeftTargetPotition(int potition)
    {
        leftMotor1.setTargetPosition(potition);
        leftMotor2.setTargetPosition(potition);
    }

    public void setRightTargetPotition(int potition)
    {
        rightMotor1.setTargetPosition(potition);
        rightMotor2.setTargetPosition(potition);
    }

    public void setLeftDirection(DcMotorSimple.Direction direction)
    {
        leftMotor1.setDirection(direction);
        leftMotor2.setDirection(direction);
    }

    public void setRightDirection(DcMotorSimple.Direction direction)
    {
        rightMotor1.setDirection(direction);
        rightMotor2.setDirection(direction);
    }

    public void debug(Telemetry telemetry)
    {
        telemetry.addData("Left Motor 1", "Power: " + leftMotor1.getPower() + " Direction: " + leftMotor1.getDirection().name());
        telemetry.addData("Left Motor 2", "Power: " + leftMotor2.getPower() + " Direction: " + leftMotor2.getDirection().name());
        telemetry.addData("Right Motor 1", "Power: " + rightMotor1.getPower() + " Direction: " + rightMotor1.getDirection().name());
        telemetry.addData("Right Motor 2", "Power: " + rightMotor2.getPower() + " Direction: " + rightMotor2.getDirection().name());
    }
}
