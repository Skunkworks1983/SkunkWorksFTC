package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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

@Autonomous(name="AutonomousEncoder", group="Encoder")
public class Final extends AutonomousEncoder
{
    private FlyWheel flyWheel = new FlyWheel();

    @Override
    public void encoders() throws InterruptedException
    {
        flyWheel.init(hardwareMap);
        Servo servo = hardwareMap.servo.get("fwServo");

        flyWheel.setPower(1);

        sleep(5555);
        servo.setPosition(1);
        sleep(3000);
        servo.setPosition(0);

        encoderDrive(.2, -3, 3, 3);
        encoderDrive(.2, 50, 50, 3);
    }
}
