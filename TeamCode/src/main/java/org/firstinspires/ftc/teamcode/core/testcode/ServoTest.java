package org.firstinspires.ftc.teamcode.core.testcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.core.BaseOpMode;

/**
 * Created by Adam.
 * January 09, 2017 at 4:15 PM
 */

@Autonomous(name="Servo", group="Time")
public class ServoTest extends BaseOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        waitForStart();

        CRServo servo1 = hardwareMap.crservo.get("servo1");
        //Servo servo2 = hardwareMap.servo.get("servo2");

        servo1.setPower(1);
        //servo2.setPosition(1);

        sleep(2000);

        servo1.setPower(0);
        //servo2.setPosition(0);

        idle();
    }
}

