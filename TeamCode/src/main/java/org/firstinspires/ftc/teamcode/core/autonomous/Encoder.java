package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;

/**
 * Created by Adam.
 * November 07, 2016 at 7:05 PM
 */

@Autonomous(name="Encoder", group="Pushbot")
public class Encoder extends LinearOpMode
{
    //MotorsHardware robot = new MotorsHardware();
    public DcMotor leftMotor1;
    public DcMotor leftMotor2;
    public DcMotor rightMotor1;
    public DcMotor rightMotor2;
    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1120;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 0.7777778;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference 3.875 4.0
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.2;
    static final double TURN_SPEED = 0.25;

    @Override
    public void runOpMode() throws InterruptedException
    {
        //robot.init(hardwareMap);
        leftMotor1 = hardwareMap.dcMotor.get("left_drive1");
        leftMotor2 = hardwareMap.dcMotor.get("left_drive2");
        rightMotor1 = hardwareMap.dcMotor.get("right_drive1");
        rightMotor2 = hardwareMap.dcMotor.get("right_drive2");
        rightMotor1.setDirection(DcMotor.Direction.REVERSE);
        rightMotor2.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Resetting Encoders...");
        telemetry.update();

//        robot.setLeftMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.setRightMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

//        robot.setLeftMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        robot.setRightMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d & %7d : %7d %7d",
                leftMotor1.getCurrentPosition(),
                leftMotor2.getCurrentPosition(),
                rightMotor1.getCurrentPosition(),
                rightMotor2.getCurrentPosition());
        telemetry.update();

        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
//        encoderDrive(DRIVE_SPEED,  48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
//        encoderDrive(TURN_SPEED,   12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
//        encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout
       /** These might be location on the field but I'm not sure */
        encoderDrive(DRIVE_SPEED, 1, 1, 5.0);

        //sleep(1000);     // pause for servos to move

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) throws InterruptedException
    {
        int newLeftTarget1;
        int newLeftTarget2;
        int newRightTarget1;
        int newRightTarget2;

        if(opModeIsActive())
        {
            // Determine new target position, and pass to motor controller
            newLeftTarget1 = leftMotor1.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newLeftTarget2 = leftMotor2.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget1 = rightMotor1.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            newRightTarget2 = rightMotor2.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            leftMotor1.setTargetPosition(newLeftTarget1);
            leftMotor2.setTargetPosition(newLeftTarget2);
            rightMotor1.setTargetPosition(newRightTarget1);
            rightMotor2.setTargetPosition(newRightTarget2);

            // Turn On RUN_TO_POSITION
//            robot.setLeftMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.setRightMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            double abs = Math.abs(speed);
//            robot.setLeftPower(Math.abs(speed));
//            robot.setRightPower(Math.abs(speed));
            leftMotor1.setPower(abs);
            leftMotor2.setPower(abs);
            rightMotor1.setPower(abs);
            rightMotor2.setPower(abs);


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftMotor1.isBusy() && leftMotor2.isBusy() && rightMotor1.isBusy() && rightMotor2.isBusy()))
            {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget1,  newRightTarget1);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        leftMotor1.getCurrentPosition(),
                        rightMotor2.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
//            robot.setLeftPower(0);
//            robot.setRightPower(0);
            leftMotor1.setPower(0);
            leftMotor2.setPower(0);
            rightMotor1.setPower(0);
            rightMotor2.setPower(0);


            // Turn off RUN_TO_POSITION
//            robot.setLeftMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.setRightMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

           // sleep(250);   // optional pause after each move
        }
    }
}

