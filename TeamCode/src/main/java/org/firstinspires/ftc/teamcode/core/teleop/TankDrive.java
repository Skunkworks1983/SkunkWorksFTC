package org.firstinspires.ftc.teamcode.core.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.core.CustomOpMode;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;

/**
 * Created by Adam.
 * October 26, 2016 at 7:03 PM
 */

@TeleOp(name="Tank Drive (Easier, less control)", group="Drive")
public class TankDrive extends CustomOpMode
{
    MotorsHardware motors;
    boolean toggled;

    @Override
    public void init()
    {
        motors = new MotorsHardware();
        motors.init(hardwareMap);

        buttonUp = false;
        toggled = false;
    }

    @Override
    public void loop()
    {
        // Check for powering up or down
        powerUpDown();

        // Left stick is power for left side, etc
        float leftPower = -gamepad1.left_stick_y * getPower();
        float rightPower = -gamepad1.right_stick_y * getPower();

        if(leftPower == 0 && rightPower == 0)
        {
            leftPower = -gamepad2.left_stick_y * getPower();
            rightPower = -gamepad2.right_stick_y * getPower();
        }

        //Set the power of the motors with the gamepad values
        motors.setLeftPower(leftPower);
        motors.setRightPower(rightPower);
    }
}
