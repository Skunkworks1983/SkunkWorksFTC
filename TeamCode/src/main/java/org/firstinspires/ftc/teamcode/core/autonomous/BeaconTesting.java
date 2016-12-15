package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Adam.
 * December 12, 2016 at 8:16 PM
 */

@Autonomous(name="Beacon Test", group="Test")
public class BeaconTesting extends AutonomousEncoder
{
    public BeaconTesting()
    {
        super(true);
    }

    @Override
    public void encoders() throws InterruptedException
    {
        // drive forward
        encoderDrive(.2, 10, 10, 3);
        gyroTurn(.2, 90);
        encoderDrive(.2, 10, 10, 2);
    }
}
