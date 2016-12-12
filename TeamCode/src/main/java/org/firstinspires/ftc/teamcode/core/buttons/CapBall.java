package org.firstinspires.ftc.teamcode.core.buttons;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.buttons.api.Button;

/**
 * Created by Adam.
 * December 10, 2016 at 2:42 PM
 */

public class CapBall extends Button
{
    public CapBall(HardwareMap hardwareMap)
    {

    }

    public boolean run(boolean execute, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry)
    {
        if(gamepad1.b || gamepad2.b)
        {
            if(execute)

            return true;
        }
        return false;
    }
}
