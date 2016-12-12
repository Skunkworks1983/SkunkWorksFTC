package org.firstinspires.ftc.teamcode.core.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.core.ButtonsOpMode;
import org.firstinspires.ftc.teamcode.core.buttons.FlyWheelButton;

/**
 * Created by Adam.
 * December 09, 2016 at 3:19 PM
 */

@TeleOp(name="Fly Wheel", group="Drive")
@Disabled
public class FlyWheelDrive extends ButtonsOpMode
{
    @Override
    public void init()
    {
        // Need a good 5 or 6 seconds for it to get fully powered
        manager.add(new FlyWheelButton(hardwareMap));
    }

    @Override
    public void loop()
    {
        buttons();
    }
}
