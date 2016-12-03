package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.core.buttons.api.ButtonManager;
import org.firstinspires.ftc.teamcode.core.utils.FlyWheel;

/**
 * Created by Adam.
 * October 29, 2016 at 10:36 AM
 */

// Only use for TeleOp
public abstract class CustomOpMode extends OpMode
{
    private FlyWheel flyWheel;
    public boolean buttonUp2;
    public ButtonManager manager;

    public CustomOpMode()
    {
        manager = new ButtonManager();
        flyWheel = new FlyWheel();
        buttonUp2 = true;
    }

    public void buttons()
    {
        manager.run(gamepad1, gamepad2, telemetry);
    }
}
