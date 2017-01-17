package org.firstinspires.ftc.teamcode.core.autonomous;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.teamcode.core.utils.FlyWheel;

/**
 * Created by Adam.
 * January 11, 2017 at 4:04 PM
 */

@Autonomous(name = "Vufoira (Red)", group = "Vuforia")
public class VuforiaDriveToPicRed extends AutonomousEncoder
{
    ColorSensor sensorRGB;

    @Override
    public void encoders() throws InterruptedException
    {
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
        //cdim = hardwareMap.deviceInterfaceModule.get("dim");

        // set the digital channel to output mode.
        // remember, the Adafruit sensor is actually two devices.
        // It's an I2C sensor and it's also an LED that can be turned on or off.
        //cdim.setDigitalChannelMode(LED_CHANNEL, DigitalChannelController.Mode.OUTPUT);

        // get a reference to our ColorSensor object.
        sensorRGB = hardwareMap.colorSensor.get("colorSensor");

        if(sensorRGB == null)
        {
            telemetry.addData("Null", "");
            telemetry.update();
        }

        double left = 0;
        double right = 0;

        VuforiaTrackable beacon = beacons.get(3);   // gears

        beacons.activate();

        FlyWheel flyWheel = new FlyWheel();
        flyWheel.init(hardwareMap);
        Servo servo = hardwareMap.servo.get("fwServo");

        tel("Starting up fly wheel...");
        flyWheel.setPower(1);
        sleep(7000);

        tel("Shooting balls!");
        servo.setPosition(1);
        sleep(900);

        tel("Finishing shooting, ready to move!");
        flyWheel.setPower(0);
        servo.setPosition(0);
        sleep(900);

        tel("Running");
        encoderDrive(.2, 10, 10, 2);
        encoderDrive(.2, -25, 25, 2.2);
        encoderDrive(.2, -51, -51, 2);
        encoderDrive(.2, 12.5, -12.5, 2.2);

        while(opModeIsActive())
        {

            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beacon.getListener()).getPose();

            if(pose != null)
            {
                telemetry.addData(beacon.getName() + "is visible?", ((VuforiaTrackableDefaultListener) beacon.getListener()).isVisible());

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
                telemetry.addData(beacon.getName() + " Degrees", degreesToTurn);
                double distance = Math.sqrt(translation.get(2)*translation.get(2) + translation.get(0)*translation.get(0));  // Pythagoras calc of hypotenuse
                telemetry.addData(beacon.getName() + " Distance", distance);


                if(distance < 200)
                {
                    left = 0;
                    right = 0;
                }

                else if (degreesToTurn > 10) //turn right
                {
                    left = 0.2;
                    right = 0.1;
                }

                else if (degreesToTurn < -10)  //turn left
                {
                    left = 0.1;
                    right = 0.2;
                }

                else // go straight
                {
                    left = 0.2;
                    right = 0.2;
                }

                motors.setLeftPower(-left);
                motors.setRightPower(-right);

                if(left == 0)
                {
                    Color.RGBToHSV((sensorRGB.red() * 255) / 800, (sensorRGB.green() * 255) / 800, (sensorRGB.blue() * 255) / 800, hsvValues);


                    if(sensorRGB.blue() > 250)
                    {
                        tel("Is Blue");
                        run(-.15, .15, .8);
                        run(0, 0, 1);
                        run(.15, -.15, .8);
                    }

                    else
                    {
                        tel("Is Red");
                        run(.15, -.15, .8);
                        run(0, 0, 1);
                        run(-.15, .15, .8);
                    }

                    relativeLayout.post(new Runnable() {
                        public void run() {
                            relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                        }
                    });

                    if(beacon.getName().equals(beacons.get(18).getName()))
                        break;

                    run(0, 0, 2.5);
                    run(-.3, -.3, 1.2);
                    run(.2, -.2, 1.1);
                    run(.25, .25, 2.3);
                    run(-.2, .2, 1);
                    beacon = beacons.get(1);
                }
            }

            telemetry.update();
            idle();
        }
    }

    String format(OpenGLMatrix transformationMatrix)
    {
        return transformationMatrix.formatAsTransform();
    }
}

