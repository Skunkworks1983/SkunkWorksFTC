package org.firstinspires.ftc.teamcode.core.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.core.CustomOpMode;
import org.firstinspires.ftc.teamcode.core.utils.Sound;

/**
 * Created by Adam.
 * October 26, 2016 at 7:03 PM
 */

@TeleOp(name="Fly Wheel", group="Testing")
public class FlyWheelAlone extends CustomOpMode
{
    private Sound sound;

    @Override
    public void init()
    {
        sound = new Sound(hardwareMap);
    }

    @Override
    public void loop()
    {
        if(gamepad1.y)
            sound.playExplosion();

        flyWheel();
        finish();
    }

}
