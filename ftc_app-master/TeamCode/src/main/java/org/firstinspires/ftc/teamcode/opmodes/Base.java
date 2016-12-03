package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by s-4041730 on 11/5/2016.
 */

public abstract class Base extends LinearOpMode {
    public DcMotor MotorFL  = null;
    public DcMotor MotorFR  = null;
    public DcMotor MotorBL  = null;
    public DcMotor MotorBR  = null;

    HardwareMap hwMap           = null;
    private ElapsedTime period  = new ElapsedTime();

    public GyroSensor sensorGyro;  //General Gyro Sensor allows us to point to the sensor in the configuration file.
    public ModernRoboticsI2cGyro mrGyro;  //ModernRoboticsI2cGyro allows us to .getIntegratedZValue()

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        MotorFL = hwMap.dcMotor.get("leftFront");
        MotorFR = hwMap.dcMotor.get("rightFront");
        MotorBL = hwMap.dcMotor.get("leftBack");
        MotorBR = hwMap.dcMotor.get("rightBack");

        MotorFL.setDirection(DcMotor.Direction.FORWARD);
        MotorBL.setDirection(DcMotor.Direction.FORWARD);
        MotorFR.setDirection(DcMotor.Direction.REVERSE);
        MotorBR.setDirection(DcMotor.Direction.REVERSE);


        // Set all motors to zero power
        MotorFL.setPower(0);
        MotorBL.setPower(0);
        MotorFR.setPower(0);
        MotorBR.setPower(0);
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
                MotorFL.setPower(turnSpeed);
                MotorBL.setPower(turnSpeed);
                MotorFR.setPower(-turnSpeed);
                MotorBR.setPower(-turnSpeed);
            }

            if (zAccumulated < target) {  //if gyro is positive, we will turn left
                MotorFL.setPower(-turnSpeed);
                MotorBL.setPower(-turnSpeed);
                MotorFR.setPower(turnSpeed);
                MotorBR.setPower(turnSpeed);
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

        MotorFL.setPower(0);
        MotorBL.setPower(0);
        MotorFR.setPower(0);
        MotorBR.setPower(0);

    }

}
