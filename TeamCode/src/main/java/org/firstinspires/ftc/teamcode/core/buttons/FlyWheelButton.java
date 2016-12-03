package org.firstinspires.ftc.teamcode.core.buttons;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.core.buttons.api.Button;
import org.firstinspires.ftc.teamcode.core.utils.FlyWheel;

/**
 * Created by Adam.
 * December 03, 2016 at 11:19 AM
 */

public class FlyWheelButton extends Button
{
    private FlyWheel flyWheel;

    public FlyWheelButton(HardwareMap hardwareMap)
    {
        flyWheel = new FlyWheel();
        flyWheel.init(hardwareMap);
    }

    public boolean run(boolean execute, Gamepad gamepad1, Gamepad gamepad2, Telemetry telemetry)
    {
        telemetry.addData("Fly wheel activated?", flyWheel.isActive());

        if(gamepad1.a || gamepad2.a)
        {
            if(execute)
                flyWheel.toggleActive();
            return true;
        }
        return false;
    }
}
