package org.firstinspires.ftc.teamcode.opmodes.Teleop;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.teamcode.opmodes.Base;

//import org.firstinspires.ftc.teamcode.LBHW;

@TeleOp(name="Gyro", group="")
@Disabled
public class GyroTesting extends Base {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap);

        int mem = 0;  //Desired angle to turn to

        runWithoutEncoders();

        while (mrGyro.isCalibrating()) {

        }
        telemetry.addData(">", "Gyro is calibrated");
        telemetry.update();
        waitForStart();  //Wait for play button to be pressed

        while (opModeIsActive()) {
            if (gamepad1.a)
                mem = mem + 45;
            if (gamepad1.b)
                mem = mem - 45;

            turnAbsolute(mem);

            idle();
        }
    }
}