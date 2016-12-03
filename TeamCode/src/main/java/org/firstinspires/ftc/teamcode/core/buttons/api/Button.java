package org.firstinspires.ftc.teamcode.core.buttons.api;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Adam.
 * December 03, 2016 at 10:22 AM
 */

public abstract class Button
{
    // We'll return true if an action happened
    public abstract boolean run(boolean execute, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry);
}
