package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import org.firstinspires.ftc.teamcode.core.BaseOpMode;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;

import java.util.Random;

/**
 * Created by Adam.
 * December 12, 2016 at 8:16 PM
 */

@Autonomous(name="Beacon Test", group="Test")
public class BeaconTesting extends BaseOpMode
{
    OpticalDistanceSensor distanceSensor;

//    public BeaconTesting()
//    {
//        super(true);
//    }

    @Override
    public void runOpMode() throws InterruptedException
    {
        motors = new MotorsHardware();
        motors.init(hardwareMap);

        distanceSensor = hardwareMap.opticalDistanceSensor.get("ods");
        distanceSensor.enableLed(true);

        // drive forward
        //encoderDrive(.2, 10, 10, 3);
        //gyroTurn(.2, 90);
        //encoderDrive(.2, 3, -3, 1);

        waitForStart();

        motors.setLeftPower(.2);
        motors.setRightPower(.2);

        while(opModeIsActive())
        {
            if(distanceSensor.getRawLightDetected() > 0.1)
            {
                telemetry.addData("Done", "");
                telemetry.addData(distanceSensor.getRawLightDetected() + "", "Raw");
                tel(distanceSensor.getLightDetected() + "");
                motors.setLeftPower(0);
                motors.setRightPower(0);
                break;
            }

            telemetry.addData(distanceSensor.getRawLightDetected() + "", "");
            tel(distanceSensor.getLightDetected() + "");

            idle();
        }
    }
}
