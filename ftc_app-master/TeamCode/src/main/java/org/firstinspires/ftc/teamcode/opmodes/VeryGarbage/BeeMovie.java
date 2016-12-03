/*
//meow
Copyright (c) 2016 Robert Atkinson
//meow

//meow
All rights reserved.
//meow

//meow
Redistribution and use in source and binary forms, with or without modification,
//meow
are permitted (subject to the limitations in the disclaimer below) provided that
//meow
the following conditions are met:
//meow

//meow
Redistributions of source code must retain the above copyright notice, this list
//meow
of conditions and the following disclaimer.
//meow

//meow
Redistributions in binary form must reproduce the above copyright notice, this
//meow
list of conditions and the following disclaimer in the documentation and/or
//meow
other materials provided with the distribution.
//meow

//meow
Neither the name of Robert Atkinson nor the names of his contributors may be used to
//meow
endorse or promote products derived from this software without specific prior
//meow
written permission.
//meow

//meow
NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
//meow
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
//meow
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
//meow
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
//meow
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
//meow
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
//meow
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
//meow
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
//meow
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
//meow
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
//meow
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//meow
*/
//meow
package org.firstinspires.ftc.teamcode.opmodes.VeryGarbage;
//meow

//meow
//meow

//meow

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.opmodes.Base;

//meow
//meow
//meow
//meow
//meow
//meow

//meow


@TeleOp(name="beeeeemovie", group="Linear Opmode")  // @Autonomous(...) is the other common choice
//meow
public class BeeMovie extends LinearOpMode {
//meow

//meow
    /* Declare OpMode members. */
//meow
    @Override
//meow
    public void runOpMode() throws InterruptedException {
//meow
        telemetry.addData("Status", "Initialized");
//meow
        telemetry.update();
//meow
        waitForStart();
//meow

//meow
        // run until the end of the match (driver presses STOP)
//meow
        while (opModeIsActive()) {
//meow
            telemetry.addData("", "According to all known laws of aviation,     there is no way a bee should be able to fly.     Its wings are too small to get its fat little body off the ground.     The bee, of course, flies anyway    because bees don't care what humans think is impossible.     Yellow, black. Yellow, black. Yellow, black. Yellow, black.     Ooh, black and yellow! Let's shake it up a little.     Barry! Breakfast is ready!     Ooming!     Hang on a second.     Hello?     - Barry? - Adam?     - Oan you believe this is happening? - I can't. I'll pick you up.     Looking sharp.     Use the stairs. Your father paid good money for those.     Sorry. I'm excited.     Here's the graduate. We're very proud of you, son.     A perfect report card, all B's.     Very proud.     Ma! I got a thing going here.     - You got lint on your fuzz. - Ow! That's me!     - Wave to us! We'll be in row 118,000. - Bye!     Barry, I told you, stop flying in the house!     - Hey, Adam. - Hey, Barry.     - Is that fuzz gel? - A little. Special day, graduation.     Never thought I'd make it.     Three days grade school, three days high school.     Those were awkward.     Three days college. I'm glad I took a day and hitchhiked around the hive.     You did come back different.     - Hi, Barry. - Artie, growing a mustache? Looks good.     - Hear about Frankie? - Yeah.     - You going to the funeral? - No, I'm not going.     Everybody knows, sting someone, you die.     Don't waste it on a squirrel. Such a hothead.     I guess he could have just gotten out of the way.     I love this incorporating an amusement park into our day.     That's why we don't need vacations.     Boy, quite a bit of pomp... under the circumstances.     - Well, Adam, today we are men. - We are!     - Bee-men. - Amen!     Hallelujah!     Students, faculty, distinguished bees,     please welcome Dean Buzzwell.     Welcome, New Hive Oity graduating class of...     ...9:15.     That concludes our ceremonies.     And begins your career at Honex Industries!     Will we pick ourjob today?     I heard it's just orientation.     Heads up! Here we go.     Keep your hands and antennas inside the tram at all times.     - Wonder what it'll be like? - A little scary.     Welcome to Honex, a division of Honesco     and a part of the Hexagon Group.     This is it!     Wow.     Wow.     We know that you, as a bee, have worked your whole life     to get to the point where you can work for your whole life.     Honey begins when our valiant Pollen Jocks bring the nectar to the hive.     Our top-secret formula     is automatically color-corrected, scent-adjusted and bubble-contoured     into this soothing sweet syrup     with its distinctive golden glow you know as...     Honey!     - That girl was hot. - She's my cousin!     - She is? - Yes, we're all cousins.     - Right. You're right. - At Honex, we constantly strive     to improve every aspect of bee existence.     These bees are stress-testing a new helmet technology.     - What do you think he makes? - Not enough.     Here we have our latest advancement, the Krelman.     - What does that do? - Oatches that little strand of honey     that hangs after you pour it. Saves us millions.     Oan anyone work on the Krelman?     Of course. Most bee jobs are small ones. But bees know     that every small job, if it's done well, means a lot.     But choose carefully     because you'll stay in the job you pick for the rest of your life.     The same job the rest of your life? I didn't know that.     What's the difference?     You'll be happy to know that bees, as a species, haven't had one day off     in 27 million years.     So you'll just work us to death?     We'll sure try.     Wow! That blew my mind!     \"What's the difference?\" How can you say that?     One job forever? That's an insane choice to have to make.     I'm relieved. Now we only have to make one decision in life.     But, Adam, how could they never have told us that?     Why would you question anything? We're bees.     We're the most perfectly functioning society on Earth.     You ever think maybe things work a little too well here?     Like what? Give me one example.     I don't know. But you know what I'm talking about.     Please clear the gate. Royal Nectar Force on approach.     Wait a second. Oheck it out.     - Hey, those are Pollen Jocks! - Wow.     I've never seen them this close.     They know what it's like outside the hive.     Yeah, but some don't come back.     - Hey, Jocks! - Hi, Jocks!     You guys did great!     You're monsters! You're sky freaks! I love it! I love it!     - I wonder where they were. - I don't know.     Their day's not planned.     Outside the hive, flying who knows where, doing who knows what.     You can'tjust decide to be a Pollen Jock. You have to be bred for that.");
            //meow
            telemetry.update();
        }
//meow
    }
//meow

    /**
     * This file illustrates the concept of driving a path based on Gyro heading and encoder counts.
     * It uses the common Pushbot hardware class to define the drive on the robot.
     * The code is structured as a LinearOpMode
     *
     * The code REQUIRES that you DO have encoders on the wheels,
     *   otherwise you would use: PushbotAutoDriveByTime;
     *
     *  This code ALSO requires that you have a Modern Robotics I2C gyro with the name "gyro"
     *   otherwise you would use: PushbotAutoDriveByEncoder;
     *
     *  This code requires that the drive Motors have been configured such that a positive
     *  power command moves them forward, and causes the encoders to count UP.
     *
     *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
     *
     *  In order to calibrate the Gyro correctly, the robot must remain stationary during calibration.
     *  This is performed when the INIT button is pressed on the Driver Station.
     *  This code assumes that the robot is stationary when the INIT button is pressed.
     *  If this is not the case, then the INIT should be performed again.
     *
     *  Note: in this example, all angles are referenced to the initial coordinate frame set during the
     *  the Gyro Calibration process, or whenever the program issues a resetZAxisIntegrator() call on the Gyro.
     *
     *  The angle of movement/rotation is assumed to be a standardized rotation around the robot Z axis,
     *  which means that a Positive rotation is Counter Clock Wise, looking down on the field.
     *  This is consistent with the FTC field coordinate conventions set out in the document:
     *  ftc_app\doc\tutorial\FTC_FieldCoordinateSystemDefinition.pdf
     *
     * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
     * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
     */

    @Autonomous(name="Gyro Testin 2")
    public static class AutoDriveGyro extends LinearOpMode {

        /* Declare OpMode members. */
        Base robot   = new Base();   // Use a Pushbot's hardware
        ModernRoboticsI2cGyro gyro    = null;                    // Additional Gyro device

        static final double     COUNTS_PER_MOTOR_REV    = 1220 ;    // eg: TETRIX Motor Encoder
        static final double     DRIVE_GEAR_REDUCTION    = 0.7777778 ;     // This is < 1.0 if geared UP
        static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
        static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                          (WHEEL_DIAMETER_INCHES * 3.1415);

        // These constants define the desired driving/control characteristics
        // The can/should be tweaked to suite the specific robot drive train.
        static final double     DRIVE_SPEED             = 0.2;     // Nominal speed for better accuracy.
        static final double     TURN_SPEED              = 0.1;     // Nominal half speed for better accuracy.

        static final double     HEADING_THRESHOLD       = 1 ;      // As tight as we can make it with an integer gyro
        static final double     P_TURN_COEFF            = 0.1;     // Larger is more responsive, but also less stable
        static final double     P_DRIVE_COEFF           = 0.15;     // Larger is more responsive, but also less stable


        @Override
        public void runOpMode() {

            /*
             * Initialize the standard drive system variables.
             * The init() method of the hardware class does most of the work here
             */
            robot.init(hardwareMap);
            gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");

            // Ensure the robot it stationary, then reset the encoders and calibrate the gyro.
            robot.MotorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.MotorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.MotorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            robot.MotorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Send telemetry message to alert driver that we are calibrating;
            telemetry.addData(">", "Calibrating Gyro");    //
            telemetry.update();

            gyro.calibrate();

            // make sure the gyro is calibrated before continuing
            while (!isStopRequested() && gyro.isCalibrating())  {
                sleep(50);
                idle();
            }

            telemetry.addData(">", "Robot Ready.");    //
            telemetry.update();

            robot.MotorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.MotorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.MotorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.MotorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // Wait for the game to start (Display Gyro value), and reset gyro before we move..
            while (!isStarted()) {
                telemetry.addData(">", "Robot Heading = %d", gyro.getIntegratedZValue());
                telemetry.update();
                idle();
            }
            gyro.resetZAxisIntegrator();

            // Step through each leg of the path,
            // Note: Reverse movement is obtained by setting a negative distance (not speed)
            // Put a hold after each turn
            //gyroDrive(DRIVE_SPEED, 48.0, 0.0);    // Drive FWD 48 inches
            gyroTurn( TURN_SPEED, -45.0);         // Turn  CCW to -45 Degrees
            //gyroHold( TURN_SPEED, -45.0, 0.5);    // Hold -45 Deg heading for a 1/2 second
            //gyroTurn( TURN_SPEED,  45.0);         // Turn  CW  to  45 Degrees
            //gyroHold( TURN_SPEED,  45.0, 0.5);    // Hold  45 Deg heading for a 1/2 second
            //gyroTurn( TURN_SPEED,   0.0);         // Turn  CW  to   0 Degrees
            //gyroHold( TURN_SPEED,   0.0, 1.0);    // Hold  0 Deg heading for a 1 second
            //gyroDrive(DRIVE_SPEED,-48.0, 0.0);    // Drive REV 48 inches
            //gyroHold( TURN_SPEED,   0.0, 0.5);    // Hold  0 Deg heading for a 1/2 second

            telemetry.addData("Path", "Complete");
            telemetry.update();
        }


       /**
        *  Method to drive on a fixed compass bearing (angle), based on encoder counts.
        *  Move will stop if either of these conditions occur:
        *  1) Move gets to the desired position
        *  2) Driver stops the opmode running.
        *
        * @param speed      Target speed for forward motion.  Should allow for _/- variance for adjusting heading
        * @param distance   Distance (in inches) to move from current position.  Negative distance means move backwards.
        * @param angle      Absolute Angle (in Degrees) relative to last gyro reset.
        *                   0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
        *                   If a relative angle is required, add/subtract from current heading.
        */
        public void gyroDrive ( double speed,
                                double distance,
                                double angle) {

            int     newLeftTarget;
            int     newRightTarget;
            int     newLeftTarget1;
            int     newRightTarget1;
            int     moveCounts;
            double  max;
            double  error;
            double  steer;
            double  leftSpeed;
            double  rightSpeed;

            // Ensure that the opmode is still active
            if (opModeIsActive()) {

                // Determine new target position, and pass to motor controller
                moveCounts = (int)(distance * COUNTS_PER_INCH);
                newLeftTarget = robot.MotorFL.getCurrentPosition() + moveCounts;
                newRightTarget = robot.MotorFR.getCurrentPosition() + moveCounts;
                newLeftTarget1 = robot.MotorBL.getCurrentPosition() + moveCounts;
                newRightTarget1 = robot.MotorBR.getCurrentPosition() + moveCounts;

                // Set Target and Turn On RUN_TO_POSITION
                robot.MotorFL.setTargetPosition(newLeftTarget);
                robot.MotorFR.setTargetPosition(newRightTarget);
                robot.MotorBL.setTargetPosition(newLeftTarget1);
                robot.MotorBR.setTargetPosition(newRightTarget1);

                robot.MotorFL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.MotorFR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.MotorBL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.MotorBR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                // start motion.
                speed = Range.clip(Math.abs(speed), 0.0, 1.0);
                robot.MotorFL.setPower(speed);
                robot.MotorFR.setPower(speed);
                robot.MotorBL.setPower(speed);
                robot.MotorBR.setPower(speed);

                // keep looping while we are still active, and BOTH motors are running.
                while (opModeIsActive() &&
                       (robot.MotorFL.isBusy() && robot.MotorFR.isBusy() && robot.MotorBL.isBusy() && robot.MotorBR.isBusy())) {

                    // adjust relative speed based on heading error.
                    error = getError(angle);
                    steer = getSteer(error, P_DRIVE_COEFF);

                    // if driving in reverse, the motor correction also needs to be reversed
                    if (distance < 0)
                        steer *= -1.0;

                    leftSpeed = speed - steer;
                    rightSpeed = speed + steer;

                    // Normalize speeds if any one exceeds +/- 1.0;
                    max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
                    if (max > 1.0)
                    {
                        leftSpeed /= max;
                        rightSpeed /= max;
                    }

                    robot.MotorFL.setPower(leftSpeed);
                    robot.MotorFR.setPower(rightSpeed);
                    robot.MotorBL.setPower(leftSpeed);
                    robot.MotorBR.setPower(rightSpeed);

                    // Display drive status for the driver.
                    telemetry.addData("Err/St",  "%5.1f/%5.1f",  error, steer);
                    telemetry.addData("Target",  "%7d:%7d",      newLeftTarget,  newRightTarget);
                    telemetry.addData("Actual",  "%7d:%7d",      robot.MotorFL.getCurrentPosition(),
                                                                 robot.MotorFR.getCurrentPosition(), robot.MotorBL.getCurrentPosition(), robot.MotorBR.getCurrentPosition());
                    telemetry.addData("Speed",   "%5.2f:%5.2f",  leftSpeed, rightSpeed);
                    telemetry.update();
                }

                // Stop all motion;
                robot.MotorFL.setPower(0);
                robot.MotorFR.setPower(0);
                robot.MotorBL.setPower(0);
                robot.MotorBR.setPower(0);

                // Turn off RUN_TO_POSITION
                robot.MotorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.MotorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.MotorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.MotorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }

        /**
         *  Method to spin on central axis to point in a new direction.
         *  Move will stop if either of these conditions occur:
         *  1) Move gets to the heading (angle)
         *  2) Driver stops the opmode running.
         *
         * @param speed Desired speed of turn.
         * @param angle      Absolute Angle (in Degrees) relative to last gyro reset.
         *                   0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
         *                   If a relative angle is required, add/subtract from current heading.
         */
        public void gyroTurn (  double speed, double angle) {

            // keep looping while we are still active, and not on heading.
            while (opModeIsActive() && !onHeading(speed, angle, P_TURN_COEFF)) {
                // Update telemetry & Allow time for other processes to run.
                telemetry.update();
            }
        }

        /**
         *  Method to obtain & hold a heading for a finite amount of time
         *  Move will stop once the requested time has elapsed
         *
         * @param speed      Desired speed of turn.
         * @param angle      Absolute Angle (in Degrees) relative to last gyro reset.
         *                   0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
         *                   If a relative angle is required, add/subtract from current heading.
         * @param holdTime   Length of time (in seconds) to hold the specified heading.
         */
        public void gyroHold( double speed, double angle, double holdTime) {

            ElapsedTime holdTimer = new ElapsedTime();

            // keep looping while we have time remaining.
            holdTimer.reset();
            while (opModeIsActive() && (holdTimer.time() < holdTime)) {
                // Update telemetry & Allow time for other processes to run.
                onHeading(speed, angle, P_TURN_COEFF);
                telemetry.update();
            }

            // Stop all motion;
            robot.MotorFL.setPower(0);
            robot.MotorFR.setPower(0);
            robot.MotorBL.setPower(0);
            robot.MotorBR.setPower(0);
        }

        /**
         * Perform one cycle of closed loop heading control.
         *
         * @param speed     Desired speed of turn.
         * @param angle     Absolute Angle (in Degrees) relative to last gyro reset.
         *                  0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
         *                  If a relative angle is required, add/subtract from current heading.
         * @param PCoeff    Proportional Gain coefficient
         * @return
         */
        boolean onHeading(double speed, double angle, double PCoeff) {
            double   error ;
            double   steer ;
            boolean  onTarget = false ;
            double leftSpeed;
            double rightSpeed;

            // determine turn power based on +/- error
            error = getError(angle);

            if (Math.abs(error) <= HEADING_THRESHOLD) {
                steer = 0.0;
                leftSpeed  = 0.0;
                rightSpeed = 0.0;
                onTarget = true;
            }
            else {
                steer = getSteer(error, PCoeff);
                rightSpeed  = speed * steer;
                leftSpeed   = -rightSpeed;
            }

            // Send desired speeds to motors.
            robot.MotorFL.setPower(leftSpeed);
            robot.MotorFR.setPower(rightSpeed);
            robot.MotorBL.setPower(leftSpeed);
            robot.MotorBR.setPower(rightSpeed);

            // Display it for the driver.
            telemetry.addData("Target", "%5.2f", angle);
            telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
            telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);

            return onTarget;
        }

        /**
         * getError determines the error between the target angle and the robot's current heading
         * @param   targetAngle  Desired angle (relative to global reference established at last Gyro Reset).
         * @return  error angle: Degrees in the range +/- 180. Centered on the robot's frame of reference
         *          +ve error means the robot should turn LEFT (CCW) to reduce error.
         */
        public double getError(double targetAngle) {

            double robotError;

            // calculate error in -179 to +180 range  (
            robotError = targetAngle - gyro.getIntegratedZValue();
            while (robotError > 180)  robotError -= 360;
            while (robotError <= -180) robotError += 360;
            return robotError;
        }

        /**
         * returns desired steering force.  +/- 1 range.  +ve = steer left
         * @param error   Error angle in robot relative degrees
         * @param PCoeff  Proportional Gain Coefficient
         * @return
         */
        public double getSteer(double error, double PCoeff) {
            return Range.clip(error * PCoeff, -1, 1);
        }

    }
}
//meow
