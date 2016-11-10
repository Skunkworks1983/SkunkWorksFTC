package org.firstinspires.ftc.teamcode.core.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Adam.
 * November 07, 2016 at 7:07 PM
 */

public class MotorsHardware
{
    /* Public OpMode members. */
    public DcMotor leftMotor1 = null;
    public DcMotor leftMotor2 = null;
    public DcMotor  rightMotor1 = null;
    public DcMotor  rightMotor2 = null;

    public static final double MID_SERVO =  0.5;

    /* local OpMode members. */
    HardwareMap hwMap =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap)
    {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftMotor1 = hwMap.dcMotor.get("left_drive1");
        leftMotor2 = hwMap.dcMotor.get("left_drive2");
        rightMotor1  = hwMap.dcMotor.get("right_drive1");
        rightMotor2  = hwMap.dcMotor.get("right_drive2");
        rightMotor1.setDirection(DcMotor.Direction.REVERSE);
        rightMotor2.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        setLeftPower(0);
        setRightPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
        leftMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
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

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     * @throws InterruptedException
     */
    public void waitForTick(long periodMs) throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}
