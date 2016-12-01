package org.firstinspires.ftc.teamcode.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.opmodes.Base;
/**
 * Created by s-2512810 on 11/30/2016.
 */

public class FlywheelProbably extends LinearOpMode {

    DcMotor backFlyWheel   = null;
    DcMotor frontFlyWheel  = null;

    public void runOpMode() {

        backFlyWheel  = hardwareMap.dcMotor.get("backFlyWheel");
        frontFlyWheel = hardwareMap.dcMotor.get("frontFlyWheel");

        backFlyWheel.setDirection(DcMotor.Direction.REVERSE);
        frontFlyWheel.setDirection(DcMotor.Direction.REVERSE);

        backFlyWheel.setPower(0);
        frontFlyWheel.setPower(0);

        backFlyWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontFlyWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        backFlyWheel.setPower(1);
        frontFlyWheel.setPower(1);

        boolean backWheelPwr = backFlyWheel.getPowerFloat();
        boolean frontWheelPwr = frontFlyWheel.getPowerFloat();

        telemetry.addData("Front Wheel Max Power: ", frontWheelPwr);
        telemetry.addData("Back Wheel Max Power: ", backWheelPwr);

        telemetry.update();
    }
}
