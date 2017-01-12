package org.firstinspires.ftc.teamcode.opmodes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.opmodes.CustomOpMode;

/**
 * Created by s-4041730 on 12/14/2016.
 */
@TeleOp(name = "Outreach Driving")
public class OutrachArcade extends CustomOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    DcMotor MotorFL;
    DcMotor MotorFR;
    DcMotor MotorBL;
    DcMotor MotorBR;

    boolean buttonUp;
    boolean toggled;

    boolean xisOn = false;

    float CurrentPower;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        MotorFL = hardwareMap.dcMotor.get("leftFront");
        MotorFR = hardwareMap.dcMotor.get("rightFront");
        MotorBL = hardwareMap.dcMotor.get("leftBack");
        MotorBR = hardwareMap.dcMotor.get("rightBack");

        // eg: Set the drive motor directions:
        // "Reverse" the motor that runs backwards when connected directly to the battery
        MotorFR.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        MotorBR.setDirection(DcMotor.Direction.REVERSE);
        MotorFL.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        MotorBL.setDirection(DcMotor.Direction.FORWARD);

        buttonUp = false;
        toggled = false;
        backFlyWheel.setPower(0);
        frontFlyWheel.setPower(0);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            powerUpDown();

            float xVal = gamepad1.left_stick_x * getPower();   //forward backward
            float yVal = -gamepad1.left_stick_y * getPower();   //left right

            float lPow = yVal + xVal; //calc power per motor
            float rPow = yVal - xVal;

            lPow = Range.clip(lPow, -1, 1); //Make sure values don't go over 1
            rPow = Range.clip(rPow, -1, 1);

            MotorFL.setPower(-lPow);
            MotorBL.setPower(-lPow);
            MotorFR.setPower(-rPow);
            MotorBR.setPower(-rPow); //what does this stuff mean

            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }
}
