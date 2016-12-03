package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.BaseOpMode;
import org.firstinspires.ftc.teamcode.core.utils.FlyWheel;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;

/**
 * Created by Adam.
 * December 02, 2016 at 4:12 PM
 */

@Autonomous(name="Autonomous", group="Encoder")
public class Final extends BaseOpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    private FlyWheel flyWheel = new FlyWheel();

    double countsPerInch = (1120 * 0.7777778) / (4.0 * 3.1415);

    @Override
    public void runOpMode() throws InterruptedException
    {
        motors.init(hardwareMap);
        flyWheel.init(hardwareMap);

        telemetry.addData("Status", "Resetting Encoders...");
        telemetry.update();

        //motors.setLeftMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       // motors.setRightMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        //motors.setLeftMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //motors.setRightMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
//        telemetry.addData("Path0",  "Starting at %7d & %7d : %7d %7d",
//                motors.leftMotor1.getCurrentPosition(),
//                motors.leftMotor2.getCurrentPosition(),
//                motors.rightMotor1.getCurrentPosition(),
//                motors.rightMotor2.getCurrentPosition());
        telemetry.update();

        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
//        encoderDrive(DRIVE_SPEED,  48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
//        encoderDrive(TURN_SPEED,   12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
//        encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

        flyWheel.setPower(1);
        sleep(5000);
        flyWheel.setPower(0);

        encoderDrive(.2, 47, 47, 1);

        telemetry.addData("Autonomous", "Complete");
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
            newLeftTarget1 = motors.leftMotor1.getCurrentPosition() + (int)(leftInches * countsPerInch);
            newLeftTarget2 = motors.leftMotor2.getCurrentPosition() + (int)(leftInches * countsPerInch);
            newRightTarget1 = motors.rightMotor1.getCurrentPosition() + (int)(rightInches * countsPerInch);
            newRightTarget2 = motors.rightMotor2.getCurrentPosition() + (int)(rightInches * countsPerInch);
            motors.leftMotor1.setTargetPosition(newLeftTarget1);
            motors.leftMotor2.setTargetPosition(newLeftTarget2);
            motors.rightMotor1.setTargetPosition(newRightTarget1);
            motors.rightMotor2.setTargetPosition(newRightTarget2);

            // Turn On RUN_TO_POSITION
            motors.setLeftMode(DcMotor.RunMode.RUN_TO_POSITION);
            motors.setRightMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            motors.setLeftPower(Math.abs(speed));
            motors.setRightPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motors.leftMotor1.isBusy() && motors.leftMotor2.isBusy() && motors.rightMotor1.isBusy() && motors.rightMotor2.isBusy()))
            {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget1,  newRightTarget1);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        motors.leftMotor1.getCurrentPosition(),
                        motors.rightMotor2.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            motors.setLeftPower(0);
            motors.setRightPower(0);

            // Turn off RUN_TO_POSITION
            motors.setLeftMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motors.setRightMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(100);   // optional pause after each move
        }
    }
}
