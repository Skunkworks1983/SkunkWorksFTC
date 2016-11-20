package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;

/**
 Created by Adam.
 October 12, 2016 at 8:03 PM
 */
// Build on robot phone

@Autonomous(name="Drive Forward (Medium Distance)", group="Drive Forward")
public class DriveForward2 extends LinearOpMode
{
    MotorsHardware motors;

    @Override
    public void runOpMode() throws InterruptedException
    {
        motors = new MotorsHardware();
        motors.init(hardwareMap);

        waitForStart();

        motors.setLeftPower(0.5);
        motors.setRightPower(0.5);
        sleep(1350);
    }
}
