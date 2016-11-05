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
//meow

//meow

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

//meow
//meow
//meow
//meow
//meow
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
public class BeeMovie extends LinearOpMode {
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
         MotorFL.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
//meow
        MotorBL.setDirection(DcMotor.Direction.REVERSE);
//meow
         MotorFR.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
//meow
        MotorBR.setDirection(DcMotor.Direction.FORWARD);
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
            telemetry.addData("", "According to all known laws of aviation,     there is no way a bee should be able to fly.     Its wings are too small to get its fat little body off the ground.     The bee, of course, flies anyway    because bees don't care what humans think is impossible.     Yellow, black. Yellow, black. Yellow, black. Yellow, black.     Ooh, black and yellow! Let's shake it up a little.     Barry! Breakfast is ready!     Ooming!     Hang on a second.     Hello?     - Barry? - Adam?     - Oan you believe this is happening? - I can't. I'll pick you up.     Looking sharp.     Use the stairs. Your father paid good money for those.     Sorry. I'm excited.     Here's the graduate. We're very proud of you, son.     A perfect report card, all B's.     Very proud.     Ma! I got a thing going here.     - You got lint on your fuzz. - Ow! That's me!     - Wave to us! We'll be in row 118,000. - Bye!     Barry, I told you, stop flying in the house!     - Hey, Adam. - Hey, Barry.     - Is that fuzz gel? - A little. Special day, graduation.     Never thought I'd make it.     Three days grade school, three days high school.     Those were awkward.     Three days college. I'm glad I took a day and hitchhiked around the hive.     You did come back different.     - Hi, Barry. - Artie, growing a mustache? Looks good.     - Hear about Frankie? - Yeah.     - You going to the funeral? - No, I'm not going.     Everybody knows, sting someone, you die.     Don't waste it on a squirrel. Such a hothead.     I guess he could have just gotten out of the way.     I love this incorporating an amusement park into our day.     That's why we don't need vacations.     Boy, quite a bit of pomp... under the circumstances.     - Well, Adam, today we are men. - We are!     - Bee-men. - Amen!     Hallelujah!     Students, faculty, distinguished bees,     please welcome Dean Buzzwell.     Welcome, New Hive Oity graduating class of...     ...9:15.     That concludes our ceremonies.     And begins your career at Honex Industries!     Will we pick ourjob today?     I heard it's just orientation.     Heads up! Here we go.     Keep your hands and antennas inside the tram at all times.     - Wonder what it'll be like? - A little scary.     Welcome to Honex, a division of Honesco     and a part of the Hexagon Group.     This is it!     Wow.     Wow.     We know that you, as a bee, have worked your whole life     to get to the point where you can work for your whole life.     Honey begins when our valiant Pollen Jocks bring the nectar to the hive.     Our top-secret formula     is automatically color-corrected, scent-adjusted and bubble-contoured     into this soothing sweet syrup     with its distinctive golden glow you know as...     Honey!     - That girl was hot. - She's my cousin!     - She is? - Yes, we're all cousins.     - Right. You're right. - At Honex, we constantly strive     to improve every aspect of bee existence.     These bees are stress-testing a new helmet technology.     - What do you think he makes? - Not enough.     Here we have our latest advancement, the Krelman.     - What does that do? - Oatches that little strand of honey     that hangs after you pour it. Saves us millions.     Oan anyone work on the Krelman?     Of course. Most bee jobs are small ones. But bees know     that every small job, if it's done well, means a lot.     But choose carefully     because you'll stay in the job you pick for the rest of your life.     The same job the rest of your life? I didn't know that.     What's the difference?     You'll be happy to know that bees, as a species, haven't had one day off     in 27 million years.     So you'll just work us to death?     We'll sure try.     Wow! That blew my mind!     \"What's the difference?\" How can you say that?     One job forever? That's an insane choice to have to make.     I'm relieved. Now we only have to make one decision in life.     But, Adam, how could they never have told us that?     Why would you question anything? We're bees.     We're the most perfectly functioning society on Earth.     You ever think maybe things work a little too well here?     Like what? Give me one example.     I don't know. But you know what I'm talking about.     Please clear the gate. Royal Nectar Force on approach.     Wait a second. Oheck it out.     - Hey, those are Pollen Jocks! - Wow.     I've never seen them this close.     They know what it's like outside the hive.     Yeah, but some don't come back.     - Hey, Jocks! - Hi, Jocks!     You guys did great!     You're monsters! You're sky freaks! I love it! I love it!     - I wonder where they were. - I don't know.     Their day's not planned.     Outside the hive, flying who knows where, doing who knows what.     You can'tjust decide to be a Pollen Jock. You have to be bred for that.");
            //meow
        }
//meow
    }
//meow
}
//meow
