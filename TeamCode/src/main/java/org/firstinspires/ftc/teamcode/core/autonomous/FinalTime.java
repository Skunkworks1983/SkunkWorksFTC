package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.core.utils.FlyWheel;

/**
 * Created by Adam.
 * December 02, 2016 at 4:12 PM
 */

@Autonomous(name="Autonomous (Using time)", group="Encoder")
public class FinalTime extends AutonomousEncoder
{
    @Override
    public void encoders() throws InterruptedException
    {
        FlyWheel flyWheel = new FlyWheel();
        flyWheel.init(hardwareMap);
        Servo servo = hardwareMap.servo.get("fwServo");

        tel("Starting up fly wheel...");
        flyWheel.setPower(1);
        sleep(6000);

        tel("Shooting balls!");
        servo.setPosition(1);
        sleep(3000);

        tel("Finishing shooting, ready to move!");
        flyWheel.setPower(0);
        servo.setPosition(0);

        //encoderDrive(.2, 45, 45, 0);

        motors.setLeftPower(.2);
        motors.setRightPower(.2);
        sleep(3500);

        motors.setLeftPower(0);
        motors.setRightPower(0);
    }
}
