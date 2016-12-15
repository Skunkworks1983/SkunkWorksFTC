package org.firstinspires.ftc.teamcode.core.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.core.ButtonsOpMode;
import org.firstinspires.ftc.teamcode.core.buttons.FlyWheelButton;
import org.firstinspires.ftc.teamcode.core.buttons.PowerButton;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;

/**
 * Created by Adam.
 * October 26, 2016 at 7:03 PM
 */

@TeleOp(name="Simple Tank Drive", group="Simple")
public class SimpleTankDrive extends ButtonsOpMode
{
    @Override
    public void init()
    {
        motors = new MotorsHardware();
        motors.init(hardwareMap);

        manager.add(new FlyWheelButton(hardwareMap));
    }

    @Override
    public void loop()
    {
        buttons();

        // Left stick is power for left side, etc
        float leftPower = gamepad1.left_stick_y;
        float rightPower = gamepad1.right_stick_y;

        if(leftPower == 0 && rightPower == 0)
        {
            leftPower = gamepad2.left_stick_y;
            rightPower = gamepad2.right_stick_y;
        }

        //Set the power of the motors with the gamepad values
        motors.setLeftPower(Range.clip(leftPower, -1, 1));
        motors.setRightPower(Range.clip(rightPower, -1, 1));
    }
}
