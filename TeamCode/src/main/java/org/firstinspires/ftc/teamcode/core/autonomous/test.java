package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.core.BaseOpMode;

/**
 * Created by Adam.
 * December 11, 2016 at 9:05 AM
 */

@Autonomous(name="test", group="Drive Forward")
@Disabled
public class test extends BaseOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        waitForStart();

        Servo servo = hardwareMap.servo.get("fwServo");

        servo.setPosition(.8);
        sleep(2000);
        servo.setPosition(0);
        sleep(1000);
        servo.setPosition(0);
        // Won't go all the way back.

        idle();
    }
}
