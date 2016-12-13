package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Adam.
 * December 12, 2016 at 8:16 PM
 */

@Autonomous(name="Beacon Test", group="Test")
public class BeaconTesting extends AutonomousEncoder
{
    @Override
    public void encoders() throws InterruptedException
    {
        // drive forward
        encoderDrive(.2, 38, 38, 5);
    }
}
