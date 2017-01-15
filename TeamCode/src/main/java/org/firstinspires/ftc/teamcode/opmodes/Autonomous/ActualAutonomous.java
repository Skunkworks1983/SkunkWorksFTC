package org.firstinspires.ftc.teamcode.opmodes.Autonomous;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannelController;
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
public class ActualAutonomous extends ConceptVuforiaNavigation {

    double closed = 0;
    double open = 0.5;
    double DRIVE_SPEED = 0.75;
    private ElapsedTime runtime = new ElapsedTime(); //put this in every thing which you want a runtime
    int target = 60;

    ColorSensor sensorRGB;
    DeviceInterfaceModule cdim;

    // we assume that the LED pin of the RGB sensor is connected to
    // digital port 5 (zero indexed).
    static final int LED_CHANNEL = 5;

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap);
        calibrateGyro();
        while (mrGyro.isCalibrating()) { //Ensure calibration is complete (usually 2 seconds)
            idle();
        }
        telemetry.addData(">", "Gyro Calibrated.  Press Start.");
        telemetry.update();

        // hsvValues is an array that will hold the hue, saturation, and value information.
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

        resetEverything();

        resetEncoders();
        runUsingEncoders();

        waitForStart();

        //encoderDrive(DRIVE_SPEED, 15, 15, 5.0);

        backFlyWheel.setPower(-1);
        frontFlyWheel.setPower(-1);

        //wait 5.5 seconds for full power
        sleep(7000);

        ballRelease.setPosition(open);
        sleep(CYCLE_MS);

        encoderDrive(DRIVE_SPEED, 60, 60, 10.0);

        sleep(1000);

        backFlyWheel.setPower(0);
        frontFlyWheel.setPower(0);

        turnAbsolute(135);

        encoderDrive(DRIVE_SPEED, 55, 55, 10.0);

        while (opModeIsActive()) {
            // convert the RGB values to HSV values.
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