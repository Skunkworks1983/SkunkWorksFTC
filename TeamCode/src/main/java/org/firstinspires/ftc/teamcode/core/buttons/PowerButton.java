package org.firstinspires.ftc.teamcode.core.buttons;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.buttons.api.Button;

/**
 * Created by Adam.
 * December 03, 2016 at 10:32 AM
 */

public class PowerButton extends Button
{
    private int power = 10;
    private int savedPower = 10;

    public float getPower()
    {
        return (float) power / 10;
    }

    public boolean run(boolean execute, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry)
    {
        telemetry.addData("Power", (power * 10) + "%");

        if(gamepad1.left_stick_button || gamepad2.left_stick_button)
        {
            if(execute)
            {
                if (power != 3) // If so, they probably pressed it twice not allowing it to switch back later
                    savedPower = power;
                power = 3;
            }
            return true;
        }

        if(gamepad1.right_stick_button || gamepad2.right_stick_button)
        {
            if(execute)
                power = savedPower;
            return true;
        }

        if(gamepad1.right_bumper || gamepad2.right_bumper)
        {
            if(execute && power < 10)
                power++;
            return true;
        }


        if(gamepad1.left_bumper || gamepad2.left_bumper)
        {
            if(execute && power > 3)
                power--;
            return true;
        }

        return false;
    }
}
