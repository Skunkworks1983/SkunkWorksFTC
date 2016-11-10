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
package org.firstinspires.ftc.teamcode.opmodes.Teleop;
//meow

//meow
import android.os.SystemClock;
//meow

//meow
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//meow
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//meow
import com.qualcomm.robotcore.hardware.DcMotor;
//meow
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//meow
import com.qualcomm.robotcore.util.ElapsedTime;
//meow
import com.qualcomm.robotcore.util.Range;
//meow

//meow
/**
//meow
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
//meow
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
//meow
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
//meow
 * class is instantiated on the Robot Controller and executed.
//meow
 *
//meow
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
//meow
 * It includes all the skeletal structure that all linear OpModes contain.
//meow
 *
//meow
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
//meow
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
//meow
 */
//meow

//meow
@TeleOp(name="Arcade Drive", group="Linear Opmode")  // @Autonomous(...) is the other common choice
//meow
public class TeleOpArcadeDrive extends LinearOpMode {
//meow

//meow
    /* Declare OpMode members. */
//meow
    private ElapsedTime runtime = new ElapsedTime();
//meow

//meow
    DcMotor MotorFL;
//meow
    DcMotor MotorFR;
//meow
    DcMotor MotorBL;
//meow
    DcMotor MotorBR;
//meow

//meow
    @Override
//meow
    public void runOpMode() throws InterruptedException {
//meow
        telemetry.addData("Status", "Initialized");
//meow
        telemetry.update();
//meow

//meow
        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
//meow
         * to 'get' must correspond to the names assigned during the robot configuration
//meow
         * step (using the FTC Robot Controller app on the phone).
//meow
         */
//meow
         MotorFL  = hardwareMap.dcMotor.get("leftFront");
//meow
         MotorFR = hardwareMap.dcMotor.get("rightFront");
//meow
        MotorBL = hardwareMap.dcMotor.get("leftBack");
//meow
        MotorBR = hardwareMap.dcMotor.get("rightBack");
//meow

//meow
        // eg: Set the drive motor directions:
//meow
        // "Reverse" the motor that runs backwards when connected directly to the battery
//meow
         MotorFR.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
//meow
        MotorBR.setDirection(DcMotor.Direction.REVERSE);
//meow
         MotorFL.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
//meow
        MotorBL.setDirection(DcMotor.Direction.FORWARD);
//meow

//meow
        // Wait for the game to start (driver presses PLAY)
//meow
        waitForStart();
//meow
        runtime.reset();
//meow

//meow
        // run until the end of the match (driver presses STOP)
//meow
        while (opModeIsActive()) {
//meow
            telemetry.addData("Status", "Run Time: " + runtime.toString());
//meow
            telemetry.addData("xstick", gamepad1.left_stick_x);
//meow
            telemetry.addData("ystick", gamepad1.left_stick_y);
//meow
            telemetry.addData("Buttons", "a:" + gamepad1.a + " b:" + gamepad1.b + " x:" + gamepad1.x + " y:" + gamepad1.y);
//meow
            telemetry.update();
//meow

//meow
            float xVal = -gamepad1.left_stick_x;   //forward backward
//meow
            float yVal = gamepad1.left_stick_y;   //left right
//meow

//meow
            float lPow = yVal + xVal; //calc power per motor
//meow
            float rPow = yVal - xVal;
//meow

//meow
            lPow = Range.clip(lPow, -1, 1); //Make sure values don't go over 1
//meow
            rPow = Range.clip(rPow, -1, 1);
//meow

//meow
             MotorFL.setPower(lPow);
//meow
            MotorBL.setPower(lPow);
//meow
             MotorFR.setPower(rPow);
//meow
            MotorBR.setPower(rPow); //what does this stuff mean
//meow
            MotorBR.setPower(rPow);


//meow
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
//meow
        }
//meow
    }
//meow
}
//meow
