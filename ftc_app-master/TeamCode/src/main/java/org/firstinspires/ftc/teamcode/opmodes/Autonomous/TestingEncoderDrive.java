package org.firstinspires.ftc.teamcode.opmodes.Autonomous;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s-4041730 on 12/2/2016.
 */

@Autonomous(name="encoderdrivinghple")
public class TestingEncoderDrive extends ConceptVuforiaNavigation {

    //gyro and drive
    double DRIVE_SPEED = 0.5;
    private ElapsedTime runtime = new ElapsedTime(); //put this in every thing which you want a runtime
    int target = -100;
    //OpenGLMatrix location;

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap);

        resetEncoders();
        runUsingEncoders();

        colorInit();
        vuforiaInit(); //has waitforstart

        //encoderDrive(DRIVE_SPEED, 45, 45, 10.0);

        runWithoutEncoders();
        turnAbsolute(target);

        while (opModeIsActive()){

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

                while (x > 100){

                    sleep(1000);

                    MotorBL.setPower(0.5);
                    MotorBR.setPower(0.5);
                    MotorFL.setPower(0.5);
                    MotorFL.setPower(0.5);
                }

                } else {
                    telemetry.addData("Pos", "Unknown");
                }

            telemetry.update();

            //color

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

            telemetry.update();
            //end color
        }
    }
}
