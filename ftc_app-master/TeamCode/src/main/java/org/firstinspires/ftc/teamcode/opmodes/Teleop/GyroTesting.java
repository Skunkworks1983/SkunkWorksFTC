package org.firstinspires.ftc.teamcode.opmodes.Teleop;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
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
public class GyroTesting extends Base {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap);

        int target = 0;  //Desired angle to turn to

        telemetry.addData(">", "Gyro Calibrating. Do Not move!");
        telemetry.update();
        sleep(1000);  //wait for gyro to come to rest
        mrGyro.calibrate();  //Calibrate the sensor so it knows where 0 is and what still is. DO NOT MOVE SENSOR WHILE BLUE LIGHT IS SOLID

        while (mrGyro.isCalibrating()) { //Ensure calibration is complete (usually 2 seconds)

            telemetry.addData(">", "Gyro Calibrated.  Press Start.");
            telemetry.update();

            waitForStart();  //Wait for play button to be pressed

            while (opModeIsActive()) {
                if (gamepad1.a)
                    target = target + 45;
                if (gamepad1.b)
                    target = target - 45;

                turnAbsolute(target);
            }
        }
    }
}