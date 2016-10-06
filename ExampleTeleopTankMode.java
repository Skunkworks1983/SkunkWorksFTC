package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Template: Move with TeleOP", group="Move the Robot")


public class ExampleTeleopTankMode extends OpMode {

    DcMotor left_drive;
    DcMotor right_drive;

    @Override
    public void init() {
        //get references to the motors from the hardware map
        left_drive = hardwareMap.dcMotor.get("left_drive");
        right_drive = hardwareMap.dcMotor.get("right_drive");

        //reverse the right motor
        right_drive.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        //get the values from the gamepads
        //note: pushing the stick all the way up returns -1,
        // so we need to reverse the values
        float leftY = -gamepad1.left_stick_y;
        float rightY = -gamepad1.right_stick_y;

        //set the power of the motors with the gamepad values
        left_drive.setPower(leftY);
        right_drive.setPower(rightY);
    }
}

