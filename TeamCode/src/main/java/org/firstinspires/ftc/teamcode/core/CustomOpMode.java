package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Adam.
 * October 29, 2016 at 10:36 AM
 */

public abstract class CustomOpMode extends OpMode
{
    private float power;

    public CustomOpMode()
    {
        power = 1;
    }

    public float getPower()
    {
       return power;
    }

    public void powerUpDown()
    {
        if(gamepad1.right_bumper/* power up /\ */ && power < 1)
            power += 0.1f;

        else if(gamepad1.left_bumper/* power down /\ */ && power > 0.1)
            power -= 0.1f;

        telemetry.addData("Power", (power * 100) + "%");
        telemetry.update();
    }
}
