package org.firstinspires.ftc.teamcode.opmodes.Teleop;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;

//import org.firstinspires.ftc.teamcode.LBHW;

@TeleOp(name="Gyro", group="")
public class GyroTesting extends LinearOpMode {
    DcMotor mr1;
    DcMotor mr2;
    DcMotor ml2;
    DcMotor ml1;

    GyroSensor sensorGyro;  //General Gyro Sensor allows us to point to the sensor in the configuration file.
    ModernRoboticsI2cGyro mrGyro;  //ModernRoboticsI2cGyro allows us to .getIntegratedZValue()

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

        sensorGyro = hardwareMap.gyroSensor.get("gyro");  //Point to the gyro in the configuration file
        mrGyro = (ModernRoboticsI2cGyro) sensorGyro;
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
    //This function turns a number of degrees compared to where the robot is. Positive numbers trn left.
    public void turn(int target) throws InterruptedException {
        turnAbsolute(target + mrGyro.getIntegratedZValue());
    }

    //This function turns a number of degrees compared to where the robot was when the program started. Positive numbers trn left.
    public void turnAbsolute(int target) throws InterruptedException {
        int zAccumulated = mrGyro.getIntegratedZValue();  //Set variables to gyro readings
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
            /**if (Math.abs(zAccumulated - target) < 50){ //how close are you to the target?
                slowdownSpeed = Math.abs(zAccumulated - target) *2; //set the speed based on distance (Note: distance value of 50 will give 100% speed, Distance value of 25 gives 50%, ext.)
                turnSpeed = slowdownSpeed / 100;
            }
            else {
                turnSpeed = 1;
            }
            */

            zAccumulated = mrGyro.getIntegratedZValue();  //Set variables to gyro readings
            telemetry.addData("Turn Speed", turnSpeed);
            telemetry.addData("1. accu", String.format("%03d", zAccumulated));
            telemetry.update();
            idle();
        }

        ml1.setPower(0);
        ml2.setPower(0);
        mr2.setPower(0);
        ml2.setPower(0);

    }
}