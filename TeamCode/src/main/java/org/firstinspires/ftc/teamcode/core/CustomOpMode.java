package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.core.utils.Button;
import org.firstinspires.ftc.teamcode.core.utils.ButtonTask;
import org.firstinspires.ftc.teamcode.core.utils.FlyWheel;

/**
 * Created by Adam.
 * October 29, 2016 at 10:36 AM
 */

// Only use for TeleOp
public abstract class CustomOpMode extends OpMode
{
    private FlyWheel flyWheel;
    private int power;
    private int savedPower;
    public boolean buttonUp1;
    public boolean buttonUp2;

    public CustomOpMode()
    {
        flyWheel = new FlyWheel();
        power = 10;
        savedPower = 10;
        buttonUp1 = true;
        buttonUp2 = true;
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
        if(!flyWheel.isInit())
            flyWheel.init(hardwareMap);

        if(gamepad1.a || gamepad2.a)
        {
            if(buttonUp2)
                flyWheel.toggleActive();
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
