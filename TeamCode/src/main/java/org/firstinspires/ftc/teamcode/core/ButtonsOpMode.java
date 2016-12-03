package org.firstinspires.ftc.teamcode.core;

import org.firstinspires.ftc.teamcode.core.buttons.api.ButtonManager;

/**
 * Created by Adam.
 * October 29, 2016 at 10:36 AM
 */

public abstract class ButtonsOpMode extends BaseOpMode
{
    public ButtonManager manager;

    public ButtonsOpMode()
    {
        manager = new ButtonManager();
    }

    public void buttons()
    {
        manager.run(gamepad1, gamepad2, telemetry);
    }
}
