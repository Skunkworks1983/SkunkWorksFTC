package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.core.utils.FlyWheel;

/**
 * Created by Adam.
 * December 02, 2016 at 4:12 PM
 */

@Autonomous(name="Autonomous (Hail Mary)", group="Encoder")
public class FinalTest extends AutonomousEncoder
{
    @Override
    public void encoders() throws InterruptedException
    {
        FlyWheel flyWheel = new FlyWheel();
        flyWheel.init(hardwareMap);
        Servo servo = hardwareMap.servo.get("fwServo");

        tel("Starting up fly wheel...");
        flyWheel.setPower(1);
        sleep(10000);

        tel("Shooting balls!");
        servo.setPosition(1);
        sleep(4000);

        tel("Finishing shooting, ready to move!");
        flyWheel.setPower(0);
        servo.setPosition(0);

        tel("Hail Mary");
        encoderDrive(.2, 20, 20, 2.5);
        encoderDrive(.22, -9, 9, 3);
        encoderDrive(.2, 24, 24, 4);
        encoderDrive(.2, -29, -29, 1);

    }
}