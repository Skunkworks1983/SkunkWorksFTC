package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by Adam.
 * October 31, 2016 at 7:36 PM
 */

public abstract class Button
{
    private Gamepad _type;

    public Button(Gamepad type)
    {
        _type = type;
    }

    public Gamepad getGamePadType()
    {
        return _type;
    }
}