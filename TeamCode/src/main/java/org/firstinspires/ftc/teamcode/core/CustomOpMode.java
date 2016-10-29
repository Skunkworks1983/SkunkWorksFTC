package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.core.utils.RechargeUtil;

/**
 * Created by Adam.
 * October 29, 2016 at 10:36 AM
 */

// Only use for TeleOp
public abstract class CustomOpMode extends OpMode
{
    private int power;

    public CustomOpMode()
    {
        power = 10;
    }

    public float getPower()
    {
       return (float) power / 10;
    }

    public void powerUpDown()
    {
        if(!RechargeUtil.canUse("powerUpDown"))
            return;

        if(gamepad1.right_bumper && power < 10)
        {
            power++;
            RechargeUtil.recharge("powerUpDown", 3000);
        }


        else if(gamepad1.left_bumper && power > 3)
        {
            power--;
            RechargeUtil.recharge("powerUpDown", 600);
        }

        telemetry.addData("Power", (power * 10) + "%");
        telemetry.update();
    }
}
