package org.firstinspires.ftc.teamcode.core.buttons.api;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Adam.
 * December 03, 2016 at 10:24 AM
 */

public class ButtonManager
{
    private HashSet<Button> buttons;
    private HashMap<Button, Boolean> buttonUp; //Should probably make an ID for each

    public ButtonManager()
    {
        buttons = new HashSet<>();
        buttonUp = new HashMap<>();
    }

    public void add(Button button)
    {
        buttons.add(button);
        buttonUp.put(button, true);
    }

    public void run(Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry)
    {
        for(Button button : buttons)
        {
            // Should never happen
            if(buttonUp.isEmpty())
               return;

            if(button.run(buttonUp.get(button), gamepad1, gamepad2, telemetry))
                buttonUp.put(button, false);
            else
                buttonUp.put(button, true);
        }

        telemetry.update();
    }
}
