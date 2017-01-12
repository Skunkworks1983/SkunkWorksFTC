package org.firstinspires.ftc.teamcode.opmodes;

import android.app.Activity;
import android.view.View;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by s-4041730 on 11/5/2016.
 */

public abstract class Base extends LinearOpMode {

    public DcMotor MotorFL;
    public DcMotor MotorFR;
    public DcMotor MotorBL;
    public DcMotor MotorBR;

    public DcMotor frontFlyWheel;
    public DcMotor backFlyWheel;

    public Servo beaconPress;
    public Servo ballRelease;

    //color sensor
    public ColorSensor sensorRGB;
    public DeviceInterfaceModule cdim;
    public float hsvValues[];
    public final float values[] = hsvValues;
    public final View relativeLayout = null;

    public boolean bPrevState;
    public boolean bCurrState;
    public boolean bLedOn;
    //end color sensor

    HardwareMap hwMap           = null;
    private ElapsedTime period  = new ElapsedTime();
    private ElapsedTime runtime = new ElapsedTime(); //put this in every thing which you want a runtime

    //servo
    public static final int CYCLE_MS = 100;     // period of each cycle


    //encoderdrive
    public static final double     COUNTS_PER_MOTOR_REV    = 1120;    // eg: TETRIX Motor Encoder
    public static final double     DRIVE_GEAR_REDUCTION    = 0.5;     // This is < 1.0 if geared UP, driven gear diameter / driving gear diameter
    public static final double     WHEEL_DIAMETER_INCHES   = 3.875;     // For figuring circumference
    public static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                             (WHEEL_DIAMETER_INCHES * 3.1415);
    //public static final double     DRIVE_SPEED             = 0.75; //Maximum speed with no load is around 50 inches per second, between 4 and 5 feet per second
    //public static final double     TURN_SPEED              = 0.6; //put speeds in everything with encoder drive

    //color sensor
    public static final int LED_CHANNEL = 5;

    //gyro
    public GyroSensor sensorGyro;  //General Gyro Sensor allows us to point to the sensor in the configuration file.
    public ModernRoboticsI2cGyro mrGyro;  //ModernRoboticsI2cGyro allows us to .getIntegratedZValue()

    public Base(){

    }

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        MotorFL = hwMap.dcMotor.get("leftFront");
        MotorFR = hwMap.dcMotor.get("rightFront");
        MotorBL = hwMap.dcMotor.get("leftBack");
        MotorBR = hwMap.dcMotor.get("rightBack");

        //device interface module
        cdim = hardwareMap.deviceInterfaceModule.get("dim");

        //flywheel
        backFlyWheel  = hardwareMap.dcMotor.get("backFlyWheel");
        frontFlyWheel = hardwareMap.dcMotor.get("frontFlyWheel");

        //servo
        beaconPress = hardwareMap.servo.get("beaconPress");
        //ballRelease = hardwareMap.servo.get("ballRelease");

        MotorFL.setDirection(DcMotor.Direction.FORWARD);
        MotorBL.setDirection(DcMotor.Direction.FORWARD);
        MotorFR.setDirection(DcMotor.Direction.REVERSE);
        MotorBR.setDirection(DcMotor.Direction.REVERSE);

        backFlyWheel.setDirection(DcMotor.Direction.REVERSE);
        frontFlyWheel.setDirection(DcMotor.Direction.REVERSE);

        backFlyWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontFlyWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sensorGyro = hardwareMap.gyroSensor.get("gyro");  //Point to the gyro in the configuration file
        mrGyro = (ModernRoboticsI2cGyro) sensorGyro;

        //color sensor
        sensorRGB = hardwareMap.colorSensor.get("sensor_color");

        // Set all motors to zero power
        MotorFL.setPower(0);
        MotorBL.setPower(0);
        MotorFR.setPower(0);
        MotorBR.setPower(0);

        backFlyWheel.setPower(0);
        frontFlyWheel.setPower(0);
    }

    public void calibrateGyro(){
        mrGyro.calibrate();
    }

    public void resetEncoders(){
        MotorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();
    }

    public void runUsingEncoders(){
        MotorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runWithoutEncoders(){
        MotorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        MotorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        MotorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        MotorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newFLTarget;
        int newFRTarget;
        int newBLTarget;
        int newBRTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFLTarget = MotorFL.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newFRTarget = MotorFR.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            newBLTarget = MotorBL.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newBRTarget = MotorBR.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            MotorFL.setTargetPosition(newFLTarget);
            MotorFR.setTargetPosition(newFRTarget);
            MotorBL.setTargetPosition(newBLTarget);
            MotorBR.setTargetPosition(newBRTarget);

            // Turn On RUN_TO_POSITION
            MotorFL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            MotorFR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            MotorBL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            MotorBR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            MotorFL.setPower(Math.abs(speed));
            MotorFR.setPower(Math.abs(speed));
            MotorBL.setPower(Math.abs(speed));
            MotorBR.setPower(Math.abs(speed));


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
             (runtime.seconds() < timeoutS) &&
             (MotorFL.isBusy() && MotorFR.isBusy() && MotorBL.isBusy() && MotorBR.isBusy())) {

             // Display it for the driver.
             /**telemetry.addData("Path1",  "Running to %7d :%7d", newFLTarget,  newFRTarget);
             telemetry.addData("Path2",  "Running at %7d :%7d",
             MotorFL.getCurrentPosition(),
             MotorFR.getCurrentPosition(),
             MotorBL.getCurrentPosition(),
             MotorBR.getCurrentPosition());
             telemetry.update();*/
             }

            // Stop all motion;
            MotorFL.setPower(0);
            MotorFR.setPower(0);
            MotorBL.setPower(0);
            MotorBR.setPower(0);

            // Turn off RUN_TO_POSITION
            MotorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            MotorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            MotorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            MotorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

    //This function turns a number of degrees compared to where the robot is. Positive numbers trn left.
    public void turn(int target) throws InterruptedException {
        turnAbsolute(target + mrGyro.getIntegratedZValue());
    }

    //This function turns a number of degrees compared to where the robot was when the program started. Positive numbers trn left.
    public void turnAbsolute(int target) throws InterruptedException {
        if (target < 0)
            target = 359 + target;

        int zAccumulated = mrGyro.getHeading();  //Set variables to gyro readings
        double turnSpeed = 0.5;
        //double slowdownSpeed = 0;

        while (Math.abs(zAccumulated - target) > 3) {  //Continue while the robot direction is further than three degrees from the target
            if (zAccumulated > target) {  //if gyro is positive, we will turn right
                MotorFL.setPower(turnSpeed);
                MotorBL.setPower(turnSpeed);
                MotorFR.setPower(-turnSpeed);
                MotorBR.setPower(-turnSpeed);
            }

            if (zAccumulated < target && zAccumulated > 180) {  //if gyro is positive, we will turn left
                MotorFL.setPower(-turnSpeed);
                MotorBL.setPower(-turnSpeed);
                MotorFR.setPower(turnSpeed);
                MotorBR.setPower(turnSpeed);
            }

            if (Math.abs(zAccumulated - target) > 30) {
                turnSpeed = 0.5;
            } else {
                turnSpeed = 0.3;
            }
            /**if (Math.abs(zAccumulated - target) < 50){ //how close are you to the target?
             slowdownSpeed = Math.abs(zAccumulated - target) *2; //set the speed based on distance (Note: distance value of 50 will give 100% speed, Distance value of 25 gives 50%, ext.)
             turnSpeed = slowdownSpeed / 100;
             }
             else {
             turnSpeed = 1;
             }*/

            zAccumulated = mrGyro.getHeading();  //Set variables to gyro readings
            //telemetry.addData("Turn Speed", turnSpeed);
            //telemetry.addData("1. accu", String.format("%03d", zAccumulated));
            //telemetry.update();
            idle();
        }

        MotorFL.setPower(0);
        MotorBL.setPower(0);
        MotorFR.setPower(0);
        MotorBR.setPower(0);

    }

    public void colorInit() {
        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F,0F,0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(com.qualcomm.ftcrobotcontroller.R.id.RelativeLayout);

        // bPrevState and bCurrState represent the previous and current state of the button.
        boolean bPrevState = false;
        boolean bCurrState = false;

        // bLedOn represents the state of the LED.
        boolean bLedOn = true;

        // get a reference to our DeviceInterfaceModule object.

        // set the digital channel to output mode.
        // remember, the Adafruit sensor is actually two devices.
        // It's an I2C sensor and it's also an LED that can be turned on or off.
        cdim.setDigitalChannelMode(LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);

        // get a reference <></>o our ColorSensor object.

        // turn the LED on in the beginning, just so user will know that the sensor is active.
        cdim.setDigitalChannelState(LED_CHANNEL, bLedOn);
    }

    public void resetEverything() {
        ballRelease.setPosition(0);
        sleep(CYCLE_MS);
    }

}
