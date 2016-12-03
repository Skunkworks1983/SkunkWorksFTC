package org.firstinspires.ftc.teamcode.core.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Adam.
 * December 02, 2016 at 3:23 PM
 */

public class FlyWheel
{
    public DcMotor motor1 = null;
    public DcMotor motor2 = null;
    private boolean active;

    HardwareMap hwMap = null;

    public void init(HardwareMap ahwMap)
    {
        hwMap = ahwMap;
        active = false;

        // This shouldn't happen
        if(hwMap == null)
            return;

        motor1 = hwMap.dcMotor.get("flyWheel1");
        motor2 = hwMap.dcMotor.get("flyWheel2");

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

    public void toggleActive()
    {
        setPower(active ? 0 : 1);
        active = !active;
    }

    public boolean isActive()
    {
        return active;
    }

    public String getInfo()
    {
        return motor1.getMaxSpeed() + " " + motor2.getMaxSpeed();
    }
}