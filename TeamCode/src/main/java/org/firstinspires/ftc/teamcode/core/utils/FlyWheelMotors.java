package org.firstinspires.ftc.teamcode.core.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Adam.
 * November 28, 2016 at 7:31 PM
 */

public class FlyWheelMotors
{
    public DcMotor motor1 = null;
    public DcMotor motor2 = null;

    /* local OpMode members. */
    HardwareMap hwMap = null;

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap)
    {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // This shouldn't happen
        if(hwMap == null)
            return;

        motor1 = hwMap.dcMotor.get("flyWheel1");
        motor2 = hwMap.dcMotor.get("flyWheel2");

        // Set all motors to zero power
        setPower(0);
    }

    public boolean isInit()
    {
        return hwMap != null;
    }

    public void setPower(double power)
    {
        motor1.setPower(power);
        motor2.setPower(power);
    }

    public void resetPower()
    {
        motor1.setPower(0);
        motor2.setPower(0);
        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor2.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setMode(DcMotor.RunMode mode)
    {
        motor1.setMode(mode);
        motor2.setMode(mode);
    }

    public void setDirection(DcMotorSimple.Direction direction)
    {
        motor1.setDirection(direction);
        motor2.setDirection(direction);
    }
}
