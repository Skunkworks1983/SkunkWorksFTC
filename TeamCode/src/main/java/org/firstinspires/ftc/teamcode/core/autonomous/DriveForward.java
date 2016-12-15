package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.core.BaseOpMode;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;

/**
 Created by Adam.
 October 12, 2016 at 8:03 PM
 */
// Build on motors phone

@Autonomous(name="Drive Forward (Don't use)", group="Time")
@Disabled
public class DriveForward extends BaseOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        waitForStart();

        motors.setLeftPower(0.5);
        motors.setRightPower(0.5);
        sleep(1350);
    }
}
