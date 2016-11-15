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
    MotorsHardware robot = new MotorsHardware();
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
        robot.init(hardwareMap);

        telemetry.addData("Status", "Resetting Encoders...");
        telemetry.update();

        robot.setLeftMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.setRightMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.setLeftMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.setRightMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d & %7d : %7d %7d",
                robot.leftMotor1.getCurrentPosition(),
                robot.leftMotor2.getCurrentPosition(),
                robot.rightMotor1.getCurrentPosition(),
                robot.rightMotor2.getCurrentPosition());
        telemetry.update();

        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
//        encoderDrive(DRIVE_SPEED,  48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
//        encoderDrive(TURN_SPEED,   12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
//        encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout
       /** These might be location on the field but I'm not sure */
        encoderDrive(DRIVE_SPEED, 1,  1, 1);
//        encoderDrive(TURN_SPEED, 2, -2, 4.0);

        //sleep(1000);     // pause for servos to move

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) throws InterruptedException
    {
        int newLeftTarget;
        int newRightTarget;

        if(opModeIsActive())
        {
            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftMotor1.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rightMotor1.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            robot.setLeftTargetPotition(newLeftTarget);
            robot.setRightTargetPotition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.setLeftMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.setRightMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.setLeftPower(Math.abs(speed));
            robot.setRightPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.leftMotor1.isBusy() && robot.leftMotor2.isBusy()
                            && robot.rightMotor1.isBusy() && robot.rightMotor2.isBusy()))
            {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.leftMotor1.getCurrentPosition(),
                        robot.rightMotor2.getCurrentPosition());
                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

            // Stop all motion;
            robot.setLeftPower(0);
            robot.setRightPower(0);

            // Turn off RUN_TO_POSITION
            robot.setLeftMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.setRightMode(DcMotor.RunMode.RUN_USING_ENCODER);

           // sleep(250);   // optional pause after each move
        }
    }
}

