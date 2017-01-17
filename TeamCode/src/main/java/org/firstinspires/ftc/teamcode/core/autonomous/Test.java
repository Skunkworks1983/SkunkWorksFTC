package org.firstinspires.ftc.teamcode.core.autonomous;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.core.utils.FlyWheel;

/**
 * Created by Adam.
 * December 02, 2016 at 4:12 PM
 */

@Autonomous(name="Beacon Tag", group="Encoder")
public class Test extends AutonomousEncoder
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

        sensorRGB = hardwareMap.colorSensor.get("colorSensor");

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
    }
}
