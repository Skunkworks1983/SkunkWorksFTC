package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.core.utils.Button;

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
    public boolean displayPower;
    public boolean buttonUp;
    public List<Button> buttons;

    public CustomOpMode()
    {
        power = 10;
        displayPower = true;
        buttonUp = true;
        buttons = new ArrayList<>();
    }

    public float getPower()
    {
       return (float) power / 10;
    }

    public void powerUpDown()
    {
        if(gamepad1.right_bumper || gamepad2.right_bumper)
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
