package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Adam.
 * January 11, 2017 at 4:04 PM
 */

@Autonomous(name = "encoder TEST", group = "Vuforia")
public class EncoderTest extends Base
{

    @Override
    public void runOpMode() throws InterruptedException
    {
        init(hardwareMap);
        waitForStart();
        runUsingEncoders();
        encoderDrive(.2 , 50, 50, 2);
    }
}

