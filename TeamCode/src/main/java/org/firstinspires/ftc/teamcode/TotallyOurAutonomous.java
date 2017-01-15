package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.opmodes.Base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikes on 2016-10-04.
 * OpMode to find the Gears target and drive towards it in autonomous.
 * stop when distance < 400mm.
 * Note: seems to require the bot to be within about 5 feet, any further and it can't find the target
 */

@Autonomous(name = "The Good Vuphoia", group = "vision")
public class TotallyOurAutonomous extends Base {

    OpenGLMatrix pose;
    ColorSensor sensorRGB;
    DeviceInterfaceModule cdim;

    // we assume that the LED pin of the RGB sensor is connected to
    // digital port 5 (zero indexed).
    static final int LED_CHANNEL = 5;

    @Override
    public void runOpMode() throws InterruptedException {
        double left = 0;
        double right = 0;

        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "ATnP8EP/////AAAAGcfzu5cnFUpOg1NOut+hsxBWPS05nbLtESnR77c9pS0gKqYn3ill1m4oavCEkEBIqgOnYBVp1Vsi5QPMPeXoDFG9ijcRA1ipvPSCUtTxg7i9s9Y6CkjyfGB6o7GWgD4cu+Jg6HQ2PkcC98eIBfMlIpJk3XQY7KONQ7U+VOIk8I/i8+j0iby/nQMdz9nBB7UjywXtXtGmzzYIJysgV3swj2ow9vCNrF5nZDoNJvLQw4y7h9eKCvaLxrk5QIN1lwZt9IRlciR2D/gKiodv8oyKteR+8vjyG5i5Q5iUwIMF6PctEIBM4I6cUZx8oBM9MjfwyjnbOeQWtatu5w3jTf4uTOB77Uy3P0VoQuTXw0JERPrT";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);   // four targets on the field

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");

        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(beacons);

        telemetry.addData("Vuforia", "Vuforia Initialized");
        telemetry.update();

        //color sensor
        float hsvValues[] = {0F,0F,0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(com.qualcomm.ftcrobotcontroller.R.id.RelativeLayout);

        // bPrevState and bCurrState represent the previous and current state of the button.
        boolean bPrevState = false;
        boolean bCurrState = false;

        // bLedOn represents the state of the LED.
        boolean bLedOn = true;

        // get a reference to our DeviceInterfaceModule object.
        cdim = hardwareMap.deviceInterfaceModule.get("dim");

        // set the digital channel to output mode.
        // remember, the Adafruit sensor is actually two devices.
        // It's an I2C sensor and it's also an LED that can be turned on or off.
        cdim.setDigitalChannelMode(LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);

        // get a reference <></>o our ColorSensor object.
        sensorRGB = hardwareMap.colorSensor.get("sensor_color");

        // turn the LED on in the beginning, just so user will know that the sensor is active.
        cdim.setDigitalChannelState(LED_CHANNEL, bLedOn);

        telemetry.addData(">", "Gyro Calibrating. Do Not move!");
        telemetry.update();
        init(hardwareMap);

        resetEverything();
        sleep(1000);
        mrGyro.calibrate();

        while (mrGyro.isCalibrating()) { //Ensure calibration is complete (usually 2 seconds)
            idle();
        }
        telemetry.addData(">", "Gyro Calibrated.  Press Start.");
        telemetry.update();

        waitForStart();

        resetEncoders();
        runUsingEncoders();

        /**backFlyWheel.setPower(-1);
        frontFlyWheel.setPower(-1);

        sleep(7000);

        ballRelease.setPosition(0.5);
        sleep(CYCLE_MS);

        backFlyWheel.setPower(0);
        frontFlyWheel.setPower(0);*/

        encoderDrive(0.35, -46, -46, 5.0);

        runWithoutEncoders();
        turnAbsolute(-90);

        beacons.activate();  // start tracking beacons

        while (opModeIsActive()) {

            for (VuforiaTrackable trackable : allTrackables) {

                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) trackable.getListener()).getPose();  // get gears target position

                if (pose != null) {
                    if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                        telemetry.addData(trackable.getName() + "-visible", "Yes");
                    } else {
                        telemetry.addData(trackable.getName() + "-visible", "No");  // does not seem to be invokved once target found once.
                    }
                    VectorF translation = pose.getTranslation();
                    //telemetry.addData(beacon.getName() + "-Translation", translation); // format(pose) shows both orientation and translation
                    //telemetry.addData(beacon.getName()+"-vector", pose.toVector());    // dump entire matrix

                    telemetry.addData("Pose", format(pose));
                    /* based on observation of pose, it represents the position and orientation of the target image.
                       The position is in milimeters, the orientation is of the target, not the phone.
                       eg. if the phone and target are both parallel to the floor, but the phone is offset towards one edge of the target
                       and is above the floor,
                       then the orientation is (0,0,0) but the translation will contain an offset from the centre of the target,
                       in both the vertical axis and in the other axis.
                       It's that offset that can be used to calculate the angle from the phone to the target.
                     */

                    double angle = Math.atan2(translation.get(2), translation.get(0)); // in radians
                    double degreesToTurn = Math.toDegrees(angle) + 90;                 // adjust for vertical orientation of phone
                    telemetry.addData(trackable.getName() + "-Degrees", degreesToTurn);
                    double distance = Math.sqrt(translation.get(2) * translation.get(2) + translation.get(0) * translation.get(0));  // Pythagoras calc of hypotenuse
                    telemetry.addData(trackable.getName() + "-Distance", distance);

                    if (distance < 400) { //if we're within 4 cm, stop so the color sensor can read
                        left = 0;
                        right = 0;
                    } else if (degreesToTurn > 10) {  //turn right
                        left = -0.25;
                        right = -0.1;
                    } else if (degreesToTurn < -10) { ///turn left
                        left = -0.1;
                        right = -0.25;
                    } else {  // go toward the target
                        left = -0.3;
                        right = -0.3;
                    }

                    telemetry.addData("Left Power: ", left);
                    telemetry.addData("Right Power: ", right);

                    MotorFL.setPower(left);
                    MotorBL.setPower(left);
                    MotorFR.setPower(right);
                    MotorBR.setPower(right);
                    idle();

                }
                telemetry.update();
                idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop

                Color.RGBToHSV((sensorRGB.red() * 255) / 800, (sensorRGB.green() * 255) / 800, (sensorRGB.blue() * 255) / 800, hsvValues);

                // send the info back to driver station using telemetry function.
                telemetry.addData("LED", bLedOn ? "On" : "Off");
                telemetry.addData("Clear", sensorRGB.alpha());
                telemetry.addData("Red  ", sensorRGB.red());
                telemetry.addData("Green", sensorRGB.green());
                telemetry.addData("Blue ", sensorRGB.blue());
                telemetry.addData("Hue", hsvValues[0]);

                if (sensorRGB.red() < sensorRGB.blue() && sensorRGB.green() < sensorRGB.blue()) { //If it's blue
                    telemetry.addData("status", "DO THE BLUE");
                } else if (sensorRGB.red() > sensorRGB.blue() && sensorRGB.red() > sensorRGB.green()) { //If it's not blue
                    telemetry.addData("status", "DO THE REDD");
                } else {
                    telemetry.addData("status", "STOP IT YOU ARE DOING ME I AM FRIGHTEN");
                }

                // change the background color to match the color detected by the RGB sensor.
                // pass a reference to the hue, saturation, and value array as an argument
                // to the HSVToColor method.
                relativeLayout.post(new Runnable() {
                    public void run() {
                        relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                    }
                });

                telemetry.update();
            }
        }
    }

    /**
     * A simple utility that extracts positioning information from a transformation matrix
     * and formats it in a form palatable to a human being.
     */
    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }
}