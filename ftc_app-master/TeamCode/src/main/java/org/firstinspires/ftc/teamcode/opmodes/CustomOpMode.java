package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.opmodes.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam.
 * October 29, 2016 at 10:36 AM
 */

// Only use for TeleOp
public abstract class CustomOpMode extends LinearOpMode
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
        //telemetry.addData("Welcome to the robot controller!", "");
        //telemetry.addData("", "");
        telemetry.addData("DPad Down", "Decrease max speed by 10%");
        telemetry.addData("Dpad Up", "Increase max speed by 10%");
        telemetry.update();
    }

    public float getPower() {
        return (float) power / 10;
    }

    public void buttons()
    {
        // Probally want to do something other than a loop every millisecond
//       for(buttons button : Gamepad.)
    }

    public void powerUpDown()
    {
        if(gamepad1.dpad_up)
        {
            if(buttonUp && power < 10)
                power++;
            buttonUp = false;
        }


        else if(gamepad1.dpad_down)
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