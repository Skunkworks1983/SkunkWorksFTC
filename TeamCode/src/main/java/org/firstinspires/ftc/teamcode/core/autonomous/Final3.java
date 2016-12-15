package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.core.utils.FlyWheel;

/**
 * Created by Adam.
 * December 02, 2016 at 4:12 PM
 */

@Autonomous(name="Autonomous 3 (Just Drives)", group="Encoder")
@Disabled
public class Final3 extends AutonomousEncoder
{

    @Override
    public void encoders() throws InterruptedException
    {
        encoderDrive(.2, 38, 38, 5);
    }
}
