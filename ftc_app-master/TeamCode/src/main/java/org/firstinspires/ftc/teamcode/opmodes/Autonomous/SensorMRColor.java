/* Copyright (c) 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package org.firstinspires.ftc.teamcode.opmodes.Autonomous;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 *
 * This is an example LinearOpMode that shows how to use
 * a Modern Robotics Color Sensor.
 *
 * The op mode assumes that the color sensor
 * is configured with a name of "color sensor".
 *
 * You can use the X button on gamepad1 to toggle the LED on and off.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@Autonomous(name = "Sensor: MR Color", group = "Sensor")

public class SensorMRColor extends LinearOpMode {

  ColorSensor colorSensor;    // Hardware Device Object


  @Override
  public void runOpMode() throws InterruptedException {

    // hsvValues is an array that will hold the hue, saturation, and value information.
    float hsvValues[] = {0F,0F,0F};

    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;

    // get a reference to the RelativeLayout so we can change the background
    // color of the Robot Controller app to match the hue detected by the RGB sensor.
    final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

    // bPrevState and bCurrState represent the previous and current state of the button.
    boolean bPrevState = false;
    boolean bCurrState = false;

    // bLedOn represents the state of the LED.
    boolean bLedOn = false;

    // get a reference to our ColorSensor object.
    colorSensor = hardwareMap.colorSensor.get("color sensor");

    // Set the LED in the beginning
    colorSensor.enableLed(bLedOn);

    // wait for the start button to be pressed.
    waitForStart();

    // while the op mode is active, loop and read the RGB data.
    // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
    while (opModeIsActive()) {

      // check the status of the x button on either gamepad.
      bCurrState = gamepad1.x;

      // check for button state transitions.
      if ((bCurrState == true) && (bCurrState != bPrevState))  {

        // button is transitioning to a pressed state. So Toggle LED
        bLedOn = !bLedOn;
        colorSensor.enableLed(bLedOn);
      }

      // update previous state variable.
      bPrevState = bCurrState;

      // convert the RGB values to HSV values.
      Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

      // send the info back to driver station using telemetry function.
      telemetry.addData("LED", bLedOn ? "On" : "Off");
      telemetry.addData("Clear", colorSensor.alpha());
      telemetry.addData("Red  ", colorSensor.red());
      telemetry.addData("Green", colorSensor.green());
      telemetry.addData("Blue ", colorSensor.blue());
      telemetry.addData("Hue", hsvValues[0]);

      // change the background color to match the color detected by the RGB sensor.
      // pass a reference to the hue, saturation, and value array as an argument
      // to the HSVToColor method.
      relativeLayout.post(new Runnable() {
        public void run() {
          relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
        }
      });

      telemetry.update();
      idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
    }
  }

  /**
   * This file illustrates the concept of driving a path based on encoder counts.
   * It uses the common Pushbot hardware class to define the drive on the
   * The code is structured as a LinearOpMode
   *
   * The code REQUIRES that you DO have encoders on the wheels,
   *   otherwise you would use: PushbotAutoDriveByTime;
   *
   *  This code ALSO requires that the drive Motors have been configured such that a positive
   *  power command moves them forwards, and causes the encoders to count UP.
   *
   *   The desired path in this example is:
   *   - Drive forward for 48 inches
   *   - Spin right for 12 Inches
   *   - Drive Backwards for 24 inches
   *   - Stop and close the claw.
   *
   *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
   *  that performs the actual movement.
   *  This methods assumes that each movement is relative to the last stopping place.
   *  There are other ways to perform encoder based moves, but this method is probably the simplest.
   *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
   *
   * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
   * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
   */

  @Autonomous(name="Pushbot: Auto Drive By Encoder", group="Pushbot")

  public static class PushbotAutoDriveByEncoder_Linear extends LinearOpMode {

      /* Declare OpMode members. */
      DcMotor leftMotor;
      DcMotor rightMotor;

      private ElapsedTime runtime = new ElapsedTime();

      static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: TETRIX Motor Encoder
      static final double     DRIVE_GEAR_REDUCTION    = 3.5 ;     // This is < 1.0 if geared UP
      static final double     WHEEL_DIAMETER_INCHES   = 3.0 ;     // For figuring circumference
      static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                        (WHEEL_DIAMETER_INCHES * 3.1415);
      static final double     DRIVE_SPEED             = 0.6;
      static final double     TURN_SPEED              = 0.5;

      @Override
      public void runOpMode() throws InterruptedException {

          leftMotor  = hardwareMap.dcMotor.get("left_drive");
          rightMotor = hardwareMap.dcMotor.get("right_drive");

          leftMotor.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
          rightMotor.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors

          /*
           * Initialize the drive system variables.
           * The init() method of the hardware class does all the work here
           */

          // Send telemetry message to signify robot waiting;
          telemetry.addData("Status", "Resetting Encoders");    //
          telemetry.update();

          leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
          rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
          idle();

          leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
          rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

          // Send telemetry message to indicate successful Encoder reset
          telemetry.addData("Path0",  "Starting at %7d :%7d",
                            leftMotor.getCurrentPosition(),
                            rightMotor.getCurrentPosition());
          telemetry.update();

          // Wait for the game to start (driver presses PLAY)
          waitForStart();

          // Step through each leg of the path,
          // Note: Reverse movement is obtained by setting a negative distance (not speed)
          encoderDrive(DRIVE_SPEED,  48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
          encoderDrive(TURN_SPEED,   12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
          encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

  //        leftClaw.setPosition(1.0);            // S4: Stop and close the claw.
  //        rightClaw.setPosition(0.0);
  //        sleep(1000);     // pause for servos to move  // We don't have a claw...

          telemetry.addData("Path", "Complete");
          telemetry.update();
      }

      /*
       *  Method to perfmorm a relative move, based on encoder counts.
       *  Encoders are not reset as the move is based on the current position.
       *  Move will stop if any of three conditions occur:
       *  1) Move gets to the desired position
       *  2) Move runs out of time
       *  3) Driver stops the opmode running.
       */
      public void encoderDrive(double speed,
                               double leftInches, double rightInches,
                               double timeoutS) throws InterruptedException {
          int newLeftTarget;
          int newRightTarget;

          // Ensure that the opmode is still active
          if (opModeIsActive()) {

              // Determine new target position, and pass to motor controller
              newLeftTarget = leftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
              newRightTarget = rightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
              leftMotor.setTargetPosition(newLeftTarget);
              rightMotor.setTargetPosition(newRightTarget);

              // Turn On RUN_TO_POSITION
              leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
              rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

              // reset the timeout time and start motion.
              runtime.reset();
              leftMotor.setPower(Math.abs(speed));
              rightMotor.setPower(Math.abs(speed));

              // keep looping while we are still active, and there is time left, and both motors are running.
              while (opModeIsActive() &&
                     (runtime.seconds() < timeoutS) &&
                     (leftMotor.isBusy() && rightMotor.isBusy())) {

                  // Display it for the driver.
                  telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                  telemetry.addData("Path2",  "Running at %7d :%7d",
                                              leftMotor.getCurrentPosition(),
                                              rightMotor.getCurrentPosition());
                  telemetry.update();

                  // Allow time for other processes to run.
                  idle();
              }

              // Stop all motion;
              leftMotor.setPower(0);
              rightMotor.setPower(0);

              // Turn off RUN_TO_POSITION
              leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
              rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

              //  sleep(250);   // optional pause after each move
          }
      }
  }
}
