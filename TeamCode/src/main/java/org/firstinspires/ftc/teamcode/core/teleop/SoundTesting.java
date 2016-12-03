package org.firstinspires.ftc.teamcode.core.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.core.CustomOpMode;
import org.firstinspires.ftc.teamcode.core.utils.Sound;

/**
 * Created by Adam.
 * December 02, 2016 at 4:19 PM
 */

@TeleOp(name="Sounds", group="Test")
public class SoundTesting extends CustomOpMode
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
        if(gamepad1.a)
            sound.playExplosion();
    }
}
