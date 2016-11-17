package org.firstinspires.ftc.teamcode.core.autonomous;

/**
 * Created by Adam.
 * November 16, 2016 at 7:51 PM
 */

import com.qualcomm.robotcore.hardware.DcMotor;


        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 * otherwise you would use: PushbotAutoDriveByTime;
 *
 * This code ALSO requires that the drive Motors have been configured such that a positive
 * power command moves them forwards, and causes the encoders to count UP.
 *
 * The desired path in this example is:
 * - Drive forward for 48 inches
 * - Spin right for 12 Inches
 * - Drive Backwards for 24 inches
 * - Stop and close the claw.
 *
 * The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 * that performs the actual movement.
 * This methods assumes that each movement is relative to the last stopping place.
 * There are other ways to perform encoder based moves, but this method is probably the simplest.
 * This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Encoder (Other team's)")
public class OtherEncoder extends LinearOpMode {

    /* Declare OpMode members. */
    DcMotor MotorFL; //Front Left
    DcMotor MotorFR; //Front Right
    DcMotor MotorBL; //Back Left
    DcMotor MotorBR; //Back Right

    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1120 ; // eg: AndyMark Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 0.7777778 ; // This is < 1.0 if geared UP, driven gear diameter / driving gear diameter
    static final double WHEEL_DIAMETER_INCHES = 4.0; // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.25; //Maximum speed with no load is around 50 inches per second, between 4 and 5 feet per second
    static final double TURN_SPEED = 0.25;


    @Override
    public void runOpMode() throws InterruptedException {

 /*
 * Initialize the drive system variables.
 * The init() method of the hardware class does all the work here
 */

        MotorFL = hardwareMap.dcMotor.get("left_drive1");
        MotorFR = hardwareMap.dcMotor.get("right_drive1");
        MotorBL = hardwareMap.dcMotor.get("left_drive2");
        MotorBR = hardwareMap.dcMotor.get("right_drive2");

        MotorFL.setDirection(DcMotor.Direction.FORWARD);
        MotorBL.setDirection(DcMotor.Direction.FORWARD);
        MotorFR.setDirection(DcMotor.Direction.REVERSE);
        MotorBR.setDirection(DcMotor.Direction.REVERSE);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders"); //
        telemetry.update();

        MotorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        MotorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d",
                MotorFL.getCurrentPosition(),
                MotorFR.getCurrentPosition(),
                MotorBL.getCurrentPosition(),
                MotorBR.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(DRIVE_SPEED, 1, 1, 5.0); // S1: Forward 47 Inches with 5 Sec timeout
        //encoderDrive(DRIVE_SPEED, -24, -24, 4.0); // S3: Reverse 24 Inches with 4 Sec timeout

        //robot.leftClaw.setPosition(1.0); // S4: Stop and close the claw.
        //robot.rightClaw.setPosition(0.0);
        //sleep(1000); // pause for servos to move

        telemetry.addData("Path", "Complete");
        //telemetry.addData("Ticks", MotorBL.getCurrentPosition());
        telemetry.update();
    }

 /*
 * Method to perfmorm a relative move, based on encoder counts.
 * Encoders are not reset as the move is based on the current position.
 * Move will stop if any of three conditions occur:
 * 1) Move gets to the desired position
 * 2) Move runs out of time
 * 3) Driver stops the opmode running.
 */

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
                telemetry.addData("Path1", "Running to %7d :%7d", newFLTarget, newFRTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        MotorFL.getCurrentPosition(),
                        MotorFR.getCurrentPosition(),
                        MotorBL.getCurrentPosition(),
                        MotorBR.getCurrentPosition());
                telemetry.update();
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

            // sleep(250); // optional pause after each move
        }
    }
}
