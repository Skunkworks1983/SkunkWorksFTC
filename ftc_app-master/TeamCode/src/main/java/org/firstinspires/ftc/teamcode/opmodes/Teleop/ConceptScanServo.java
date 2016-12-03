package org.firstinspires.ftc.teamcode.opmodes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
//up 98, down 0 (facing motor controllers, on the right)
//up 158, down 251 (facing motor controllers, on the left)
/**
 * This OpMode scans a single servo back and forwards until Stop is pressed.
 * The code is structured as a LinearOpMode
 * INCREMENT sets how much to increase/decrease the servo position each cycle
 * CYCLE_MS sets the update period.
 *
 * This code assumes a Servo configured with the name "left claw" as is found on a pushbot.
 *
 * NOTE: When any servo position is set, ALL attached servos are activated, so ensure that any other
 * connected servos are able to move freely before running this test.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@TeleOp(name = "Concept: Scan Servo", group = "Concept")
public class ConceptScanServo extends LinearOpMode {

    static final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle sort of the speed/duration
    static final int    CYCLE_MS    =   100;     // period of each cycle
    static final double Right_Max     =  98/255;     // Maximum rotational position
    static final double Right_Min     =  0.0;     // Minimum rotational position

    static final double Left_Max     =  158/255;     // Maximum rotational position
    static final double Left_Min     =  251/255;     // Minimum rotational position

    // Define class members
    Servo   servoRight;
    Servo   servoLeft;
    double  positionRight = Right_Min; // Start at minimum position
    double  positionLeft  = Left_Min;


    @Override
    public void runOpMode() {

        // Connect to servo (Assume PushBot Left Hand)
        // Change the text in quotes to match any servo name on your robot.
        servoRight = hardwareMap.servo.get("servoRight");
        servoLeft = hardwareMap.servo.get("servoLeft");

        // Wait for the start button
        telemetry.addData(">", "Press Start to scan Servo." );
        telemetry.update();
        waitForStart();


        // Scan servo till stop pressed.
        while(opModeIsActive()){

            if (gamepad2.dpad_up){
                // Keep stepping up until we hit the max value.
                positionRight += INCREMENT ;
                if (positionRight >= Right_Max ) {
                    positionRight = Right_Max;
                }
                positionLeft += INCREMENT ;
                if (positionLeft >= Left_Max ) {
                    positionRight = Left_Max;
                }
            }

            if (gamepad2.dpad_down) {
                // Keep stepping down until we hit the min value.
                positionLeft -= INCREMENT;
                if (positionLeft <= Left_Min) {
                    positionLeft = Left_Min;
                }
                // Keep stepping down until we hit the min value.
                positionRight -= INCREMENT;
                if (positionRight <= Right_Min) {
                    positionRight = Right_Min;
                }
            }


            // Display the current value
            telemetry.addData("Right Servo Position", "%5.2f", positionRight);
            telemetry.addData("Left Servo Position", "%5.2f", positionLeft);
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();

            // Set the servo to the new position and pause;
            servoRight.setPosition(positionRight);
            servoLeft.setPosition(positionLeft);
            sleep(CYCLE_MS);
            idle();
        }

        // Signal done;
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
