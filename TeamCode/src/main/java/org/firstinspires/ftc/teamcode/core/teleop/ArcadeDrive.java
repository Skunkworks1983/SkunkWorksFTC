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
@TeleOp(name="Arcade Drive (Harder, more control)", group="Drive")
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
        flyWheel();
        finish();

        float x = gamepad1.left_stick_x;
        float y = -gamepad1.left_stick_y;

        if(x == 0 && y == 0)
        {
            x = gamepad2.left_stick_x;
            y = gamepad2.left_stick_y;
        }

        //calculate the power needed for each motor
        float leftPower = y + x;
        float rightPower = y - x;

        motor.setLeftPower(Range.clip(leftPower * getPower(), -1, 1));
        motor.setRightPower(Range.clip(-rightPower * getPower(), -1, 1));
    }

}

