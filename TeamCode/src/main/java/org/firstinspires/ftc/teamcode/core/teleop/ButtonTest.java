package org.firstinspires.ftc.teamcode.core.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.core.ButtonsOpMode;
import org.firstinspires.ftc.teamcode.core.buttons.PowerButton;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;
import org.firstinspires.ftc.teamcode.core.utils.Sound;

/**
 * Created by Adam.
 * October 26, 2016 at 7:03 PM
 */

@TeleOp(name="Hail Mary", group="Test")
public class ButtonTest extends ButtonsOpMode
{
    MotorsHardware motors;
    private Sound sound;
    private PowerButton power;

    @Override
    public void init()
    {
        motors = new MotorsHardware();
        motors.init(hardwareMap);
        sound = new Sound(hardwareMap);

        power = new PowerButton();
        manager.add(power);
    }

    @Override
    public void loop()
    {
        buttons();
        ///telemetry.update();

        telemetry.addData("Power", power.getPower());
        telemetry.update();

        float leftPower = -gamepad1.left_stick_y;
        float rightPower = -gamepad1.right_stick_y;

        if(leftPower == 0 && rightPower == 0)
        {
            leftPower = -gamepad2.left_stick_y;
            rightPower = -gamepad2.right_stick_y;
        }

        motors.setLeftPower(Range.clip(leftPower * power.getPower(), -1, 1));
        motors.setRightPower(Range.clip(rightPower * power.getPower(), -1, 1));
    }
}
