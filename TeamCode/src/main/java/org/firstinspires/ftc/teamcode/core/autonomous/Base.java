package org.firstinspires.ftc.teamcode.core.autonomous;

        import android.app.Activity;
        import android.view.View;

        import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.ColorSensor;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
        import com.qualcomm.robotcore.hardware.DigitalChannelController;
        import com.qualcomm.robotcore.hardware.GyroSensor;
        import com.qualcomm.robotcore.hardware.HardwareMap;
        import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by s-4041730 on 11/5/2016.
 */

public abstract class Base extends LinearOpMode {

    public DcMotor MotorFL;
    public DcMotor MotorFR;
    public DcMotor MotorBL;
    public DcMotor MotorBR;

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
        MotorFL = hwMap.dcMotor.get("leftDrive1");
        MotorFR = hwMap.dcMotor.get("rightDrive1");
        MotorBL = hwMap.dcMotor.get("leftDrive2");
        MotorBR = hwMap.dcMotor.get("rightDrive2");

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

}