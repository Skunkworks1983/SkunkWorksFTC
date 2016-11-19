package org.firstinspires.ftc.teamcode.core.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.core.CustomOpMode;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;

/**
 * Created by Adam.
 * October 17, 2016 at 7:56 PM
 */
@TeleOp(name="Arcade Drive", group="Drive")
public class ArcadeDrive extends CustomOpMode
{
    MotorsHardware motor = new MotorsHardware();

    @Override
    public void init()
    {
        motor.init(hardwareMap);
    }

    @Override
    public void loop()
    {
        powerUpDown();

        motor.setLeftPower(Range.clip(gamepad1.left_stick_x * getPower(), -1, 1));
        motor.setRightPower(Range.clip(-gamepad1.left_stick_y * getPower(), -1, 1));
    }

}

