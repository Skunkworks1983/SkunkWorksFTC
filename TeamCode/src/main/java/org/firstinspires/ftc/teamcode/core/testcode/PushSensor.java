package org.firstinspires.ftc.teamcode.core.testcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * Created by Adam.
 * November 04, 2016 at 4:00 PM
 */

@Autonomous(name = "Touch Sensor", group = "Sensor")
public class PushSensor extends LinearOpMode
{

    TouchSensor touchSensor;  // Hardware Device Object

    @Override
    public void runOpMode() throws InterruptedException
    {

        // get a reference to our Light Sensor object.
        touchSensor = hardwareMap.touchSensor.get("touch_sensor");
        int counter = 0;

        // wait for the start button to be pressed.
        waitForStart();

        // while the op mode is active, loop and read the light levels.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
        while (true)
        {

            // send the info back to driver station using telemetry function.
            telemetry.addData("Touch", "Is " + (touchSensor.isPressed() ? "" : "Not ") + "Pressed");

            telemetry.update();
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }
}