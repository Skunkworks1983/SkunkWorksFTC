package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.BaseOpMode;
import org.firstinspires.ftc.teamcode.core.utils.FlyWheel;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;

/**
 * Created by Adam.
 * December 02, 2016 at 4:12 PM
 */

@Autonomous(name="Autonomous", group="Encoder")
public class Final extends AutonomousEncoder
{
    @Override
    public void encoders() throws InterruptedException
    {
        FlyWheel flyWheel = new FlyWheel();
        flyWheel.init(hardwareMap);
        Servo servo = hardwareMap.servo.get("fwServo");

        tel("Starting up fly wheel...");
        flyWheel.setPower(1);
        sleep(9000);

        tel("Shooting balls!");
        servo.setPosition(1);
        sleep(4000);

        tel("Finishing shooting, ready to move!");
        flyWheel.setPower(0);
        servo.setPosition(0);

//        encoderDrive(.2, 10, 10, 2);
//        encoderDrive(.2, -26, 26, 2);
        run(.2, .2, 1);
        run(-.2, .2, 1.69);
        run(-.2, -.2, 3.15);
//        encoderDrive(.2, -62, -62, 2);
        sleep(2000);
    }
}
