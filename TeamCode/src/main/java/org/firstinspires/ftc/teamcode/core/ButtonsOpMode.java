package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.core.buttons.api.ButtonManager;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;

/**
 * Created by Adam.
 * October 29, 2016 at 10:36 AM
 */

public abstract class ButtonsOpMode extends OpMode
{
    public MotorsHardware motors;
    public ButtonManager manager;

    public ButtonsOpMode()
    {
        manager = new ButtonManager();
        motors = new MotorsHardware();
    }

    public void buttons()
    {
        manager.run(gamepad1, gamepad2, telemetry);
    }
}
