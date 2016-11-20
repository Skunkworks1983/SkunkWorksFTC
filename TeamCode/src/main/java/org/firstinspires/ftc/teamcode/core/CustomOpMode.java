package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam.
 * October 29, 2016 at 10:36 AM
 */

// Only use for TeleOp
public abstract class CustomOpMode extends OpMode
{
    private int power;
    private int savedPower;
    public boolean displayPower;
    public boolean buttonUp;

    public CustomOpMode()
    {
        power = 10;
        savedPower = 10;
        displayPower = true;
        buttonUp = true;
    }

    public float getPower()
    {
       return (float) power / 10;
    }

    public void powerUpDown()
    {
        if(gamepad1.left_stick_button || gamepad2.left_stick_button)
        {
            if(power != 3) // If so, they probally pressed it twice not allowing it to switch back later
                savedPower = power;
            power = 3;
            buttonUp = false;
        }

        else if(gamepad1.right_stick_button || gamepad2.right_stick_button)
        {
            power = savedPower;
            buttonUp = false;
        }

        else if(gamepad1.right_bumper || gamepad2.right_bumper)
        {
            if(buttonUp && power < 10)
                power++;
            buttonUp = false;
        }


        else if(gamepad1.left_bumper || gamepad2.left_bumper)
        {
            if(buttonUp && power > 3)
                power--;
            buttonUp = false;
        }

        else
            buttonUp = true;

        if(!displayPower) return;

        telemetry.addData("Power", (power * 10) + "%");
        telemetry.update();
    }
}
