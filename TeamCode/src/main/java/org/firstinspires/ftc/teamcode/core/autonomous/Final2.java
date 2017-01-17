package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.core.utils.FlyWheel;

/**
 * Created by Adam.
 * December 02, 2016 at 4:12 PM
 */

@Autonomous(name="Autonomous 2 (Just Shoots)", group="Encoder")
public class Final2 extends AutonomousEncoder
{
    private FlyWheel flyWheel;

    @Override
    public void encoders() throws InterruptedException
    {
        flyWheel = new FlyWheel();
        flyWheel.init(hardwareMap);
        Servo servo = hardwareMap.servo.get("fwServo");

        tel("Starting up fly wheel...");
        flyWheel.setPower(1);
        sleep(6000);

        tel("Shooting balls!");
        servo.setPosition(1);
        sleep(2500);

        tel("Finishing shooting.");
        flyWheel.setPower(0);
        servo.setPosition(0);
    }
}
