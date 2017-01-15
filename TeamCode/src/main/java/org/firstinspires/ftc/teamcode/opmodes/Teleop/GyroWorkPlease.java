package org.firstinspires.ftc.teamcode.opmodes.Teleop;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by s-4041730 on 1/9/2017.
 */

@TeleOp(name = "eeeeee")
@Disabled
public class GyroWorkPlease extends LinearOpMode{


    int buttonPressA = 0; //Helps ensure the button was pressed only once
    int buttonPressB = 0;

    DcMotor mr1;
    DcMotor mr2;
    DcMotor ml2;
    DcMotor ml1;
    ModernRoboticsI2cGyro mrGyro;
    GyroSensor sensorGyro;

    @Override
    public void runOpMode() throws InterruptedException {

        mr1 = hardwareMap.dcMotor.get("rightFront");
        mr2 = hardwareMap.dcMotor.get("rightBack");
        ml1 = hardwareMap.dcMotor.get("leftFront");
        ml2 = hardwareMap.dcMotor.get("leftBack");
        ml1.setDirection(DcMotor.Direction.FORWARD);
        ml2.setDirection(DcMotor.Direction.FORWARD);
        mr1.setDirection(DcMotor.Direction.REVERSE);
        mr2.setDirection(DcMotor.Direction.REVERSE);

        ml1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ml2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mr2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mr1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        int angleZ = 0;
        int target = 0;
        sensorGyro = hardwareMap.gyroSensor.get("gyro");
        mrGyro = (ModernRoboticsI2cGyro) sensorGyro;

        telemetry.addData(">", "Gyro Calibrating. Do Not move!");
        telemetry.update();
        mrGyro.calibrate();

        while (mrGyro.isCalibrating()) { //Ensure calibration is complete (usually 2 seconds)
            idle();
        }
        telemetry.addData(">", "Gyro Calibrated.  Press Start.");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            angleZ = mrGyro.getIntegratedZValue();
            telemetry.addData(">1", angleZ);
            if (gamepad1.a)
                buttonPressA++;
            if (gamepad1.b)
                buttonPressB++;
            if(buttonPressA == 1)
                target = target + 45;
            if(buttonPressB == 1)
                target = target - 45;
            telemetry.addData(">2", target);
            telemetry.update();

            turnAbsolute(target);

        }
    }

        public void turnAbsolute(int target) throws InterruptedException {
            int zAccumulated = mrGyro.getHeading();  //Set variables to gyro readings
            double turnSpeed = 0.5;
            //double slowdownSpeed = 0;

            while (Math.abs(zAccumulated - target) > 3) {  //Continue while the robot direction is further than three degrees from the target
                if (zAccumulated > target) {  //if gyro is positive, we will turn right
                    ml1.setPower(turnSpeed);
                    ml2.setPower(turnSpeed);
                    mr1.setPower(-turnSpeed);
                    mr2.setPower(-turnSpeed);
                }

                if (zAccumulated < target) {  //if gyro is positive, we will turn left
                    ml1.setPower(-turnSpeed);
                    ml2.setPower(-turnSpeed);
                    mr1.setPower(turnSpeed);
                    mr2.setPower(turnSpeed);
                }

                if (Math.abs(zAccumulated - target) > 30) {
                    turnSpeed = 0.5;
                } else {
                    turnSpeed = 0.2;
                }

                zAccumulated = mrGyro.getHeading();  //Set variables to gyro readings
                telemetry.addData("Turn Speed", turnSpeed);
                //telemetry.addData("1. accu", String.format("%03d", zAccumulated));
                telemetry.addData("Heading", zAccumulated);
                telemetry.addData("Target", target);
                telemetry.update();
                idle();
            }

            ml1.setPower(0);
            ml2.setPower(0);
            mr2.setPower(0);
            ml2.setPower(0);
            buttonPressA = 0;
            buttonPressB = 0;
        }
}
