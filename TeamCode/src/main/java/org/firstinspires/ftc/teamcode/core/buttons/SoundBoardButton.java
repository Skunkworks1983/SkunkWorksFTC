package org.firstinspires.ftc.teamcode.core.buttons;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.buttons.api.Button;
import org.firstinspires.ftc.teamcode.core.utils.Sound;

/**
 * Created by Adam.
 * December 03, 2016 at 2:11 PM
 */

public class SoundBoardButton extends Button
{
    Sound sound;

    public SoundBoardButton(Sound sounds)
    {
        sound = sounds;
    }

    @Override
    public boolean run(boolean execute, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry)
    {
        if(gamepad1.dpad_down)
        {
            if(execute)
                sound.playRickRoll();
            return true;
        }

        return false;
    }
}
