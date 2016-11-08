package org.firstinspires.ftc.teamcode.core.testcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by Adam.
 * November 04, 2016 at 3:33 PM
 */

/*
    Using hardware spot "A7"
 */
@Autonomous(name = "Distance Sensor", group = "Sensor")
@Disabled
public class DistanceSensor extends LinearOpMode
{

    OpticalDistanceSensor odsSensor;  // Hardware Device Object

    @Override
    public void runOpMode() throws InterruptedException
    {

        // get a reference to our Light Sensor object.
        odsSensor = hardwareMap.opticalDistanceSensor.get("ods");
        odsSensor.enableLed(true);

        // wait for the start button to be pressed.
        waitForStart();

        // while the op mode is active, loop and read the light levels.
        // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
        while (opModeIsActive())
        {

            // send the info back to driver station using telemetry function.
            telemetry.addData("Raw", odsSensor.getRawLightDetected());
            telemetry.addData("Normal", odsSensor.getLightDetected());

            telemetry.update();
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }
}

