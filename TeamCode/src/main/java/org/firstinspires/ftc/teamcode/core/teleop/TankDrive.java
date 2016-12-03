package org.firstinspires.ftc.teamcode.core.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.core.CustomOpMode;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;
import org.firstinspires.ftc.teamcode.core.utils.Sound;

/**
 * Created by Adam.
 * October 26, 2016 at 7:03 PM
 */

@TeleOp(name="Tank Drive (Easier, less control)", group="Drive")
public class TankDrive extends CustomOpMode
{
    MotorsHardware motors;
    boolean toggled;
    private Sound sound;

    @Override
    public void init()
    {
        motors = new MotorsHardware();
        motors.init(hardwareMap);
        sound = new Sound(hardwareMap);
        toggled = false;
    }

    @Override
    public void loop()
    {
        powerUpDown();
//        flyWheel();
        finish();

        if(gamepad1.a)
            sound.playAidan();
        else if(gamepad1.b)
            sound.playJohn();
        else if(gamepad1.x)
            sound.playNicole();

        // Left stick is power for left side, etc
        float leftPower = -gamepad1.left_stick_y;
        float rightPower = -gamepad1.right_stick_y;

        if(leftPower == 0 && rightPower == 0)
        {
            leftPower = -gamepad2.left_stick_y;
            rightPower = -gamepad2.right_stick_y;
        }

        //Set the power of the motors with the gamepad values
        motors.setLeftPower(Range.clip(leftPower * getPower(), -1, 1));
        motors.setRightPower(Range.clip(rightPower * getPower(), -1, 1));
    }
}
