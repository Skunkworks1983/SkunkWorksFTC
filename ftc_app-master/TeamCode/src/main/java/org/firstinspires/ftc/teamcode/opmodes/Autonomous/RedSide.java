package org.firstinspires.ftc.teamcode.opmodes.Autonomous;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

/**
 * Created by s-4041730 on 12/11/2016.
 */

@Autonomous(name = "Red Side")
@Disabled
public class RedSide extends ConceptVuforiaNavigation {

    double closed = 0;
    double open = 0.5;
    double DRIVE_SPEED = 0.75;
    private ElapsedTime runtime = new ElapsedTime(); //put this in every thing which you want a runtime
    int target = 60;

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap);

        resetEverything();

        resetEncoders();
        runUsingEncoders();

        //colorInit();

        vuforiaInit(); //has wait for start, put at end always


        encoderDrive(DRIVE_SPEED, 7.5, 7.5, 5.0);

        backFlyWheel.setPower(-1);
        frontFlyWheel.setPower(-1);

        //wait 5.5 seconds for full power
        sleep(7000);

        ballRelease.setPosition(open);
        sleep(CYCLE_MS);

        encoderDrive(DRIVE_SPEED, 45, 45, 10.0);

        sleep(1000);

        backFlyWheel.setPower(0);
        frontFlyWheel.setPower(0);

        while (opModeIsActive()) {

            /**
            // check the status of the x button on gamepad.
            bCurrState = gamepad1.x;

            // check for button-press state transitions.
            if ((bCurrState == true) && (bCurrState != bPrevState))  {

                // button is transitioning to a pressed state. Toggle the LED.
                bLedOn = !bLedOn;
                cdim.setDigitalChannelState(LED_CHANNEL, bLedOn);
            }

            // update previous state variable.
            bPrevState = bCurrState;

            // convert the RGB values to HSV values.
            Color.RGBToHSV((sensorRGB.red() * 255) / 800, (sensorRGB.green() * 255) / 800, (sensorRGB.blue() * 255) / 800, hsvValues);

            // send the info back to driver station using telemetry function.
            telemetry.addData("LED", bLedOn ? "On" : "Off");
            telemetry.addData("Clear", sensorRGB.alpha());
            telemetry.addData("Red  ", sensorRGB.red());
            telemetry.addData("Green", sensorRGB.green());
            telemetry.addData("Blue ", sensorRGB.blue());
            telemetry.addData("Hue", hsvValues[0]);

            // change the background color to match the color detected by the RGB sensor.
            // pass a reference to the hue, saturation, and value array as an argument
            // to the HSVToColor method.
            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update(); */





            for (VuforiaTrackable trackable : allTrackables) {
                /**
                 * getUpdatedRobotLocation() will return null if no new information is available since
                 * the last time that call was made, or if the trackable is not currently visible.
                 * getRobotLocation() will return null if the trackable is not currently visible.
                 * */
                telemetry.addData(trackable.getName(), ((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible() ? "Visible" : "Not Visible");    //

                //OpenGLMatrix location = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();

                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
            }
            /**
             * Provide feedback as to where the robot was last located (if we know).
             */
            if (lastLocation != null) {
                //  RobotLog.vv(TAG, "robot=%s", format(lastLocation));
                float[] robotLocationArray = lastLocation.getData();
                x = robotLocationArray[12];
                y = robotLocationArray[13];
                z = robotLocationArray[14];

                /**VectorF trans = location.getTranslation();
                 robotX = trans.get(0);
                 robotY = trans.get(1);*/

                rot = Orientation.getOrientation(lastLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                robotBearing = rot.thirdAngle;

                telemetry.addData("Location", "X: " + x + " " + "Y: " + y);
                telemetry.addData("Rotation", robotBearing);

            } else {
                telemetry.addData("Pos", "Unknown");
            }

            telemetry.update();
        }
     }
}
