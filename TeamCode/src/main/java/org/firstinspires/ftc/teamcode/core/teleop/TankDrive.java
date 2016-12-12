package org.firstinspires.ftc.teamcode.core.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.core.ButtonsOpMode;
import org.firstinspires.ftc.teamcode.core.buttons.FlyWheelButton;
import org.firstinspires.ftc.teamcode.core.buttons.PowerButton;
import org.firstinspires.ftc.teamcode.core.buttons.SoundBoardButton;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;
import org.firstinspires.ftc.teamcode.core.utils.Sound;

/**
 * Created by Adam.
 * October 26, 2016 at 7:03 PM
 */

@TeleOp(name="Tank Drive (Easier, less control)", group="Drive")
public class TankDrive extends ButtonsOpMode
{
    MotorsHardware motors;
    boolean toggled;
    private PowerButton power;

    @Override
    public void init()
    {
        motors = new MotorsHardware();
        motors.init(hardwareMap);
        toggled = false;

        power = new PowerButton();
        manager.add(power);
        //manager.add(new FlyWheelButton(hardwareMap));
        //manager.add(new SoundBoardButton(new Sound(hardwareMap)));
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
        motors.setLeftPower(Range.clip(leftPower * power.getPower(), -1, 1));
        motors.setRightPower(Range.clip(rightPower * power.getPower(), -1, 1));
    }
}
