package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.core.utils.FlyWheelMotors;

/**
 * Created by Adam.
 * October 29, 2016 at 10:36 AM
 */

// Only use for TeleOp
public abstract class CustomOpMode extends OpMode
{
    private FlyWheelMotors flyWheelMotors;
    private int power;
    private int savedPower;
    public boolean buttonUp1;
    public boolean buttonUp2;
    private boolean flyWheel;

    public CustomOpMode()
    {
        flyWheelMotors = new FlyWheelMotors();
        flyWheelMotors.init(hardwareMap);
        power = 10;
        savedPower = 10;
        buttonUp1 = true;
        buttonUp2 = true;
        flyWheel = false;
    }

    public float getPower()
    {
       return (float) power / 10;
    }

    public void powerUpDown()
    {
        if(gamepad1.left_stick_button || gamepad2.left_stick_button)
        {
            if(power != 3) // If so, they probably pressed it twice not allowing it to switch back later
                savedPower = power;
            power = 3;
            buttonUp1 = false;
        }

        else if(gamepad1.right_stick_button || gamepad2.right_stick_button)
        {
            power = savedPower;
            buttonUp1 = false;
        }

        else if(gamepad1.right_bumper || gamepad2.right_bumper)
        {
            if(buttonUp1 && power < 10)
                power++;
            buttonUp1 = false;
        }


        else if(gamepad1.left_bumper || gamepad2.left_bumper)
        {
            if(buttonUp1 && power > 3)
                power--;
            buttonUp1 = false;
        }

        else
            buttonUp1 = true;

        telemetry.addData("Power", (power * 10) + "%");
    }

    public void flyWheel()
    {
        if(gamepad1.a || gamepad2.a)
        {
            if(buttonUp2)
            {
                if(flyWheel)
                    flyWheelMotors.setPower(1);
                else
                    flyWheelMotors.setPower(0);

                flyWheel = !flyWheel;
            }
            buttonUp2 = false;
        }

        else
            buttonUp2 = true;

        telemetry.addData("Fly wheel activated?", flyWheel);
    }

    public void finish()
    {
        telemetry.update();
    }
}
