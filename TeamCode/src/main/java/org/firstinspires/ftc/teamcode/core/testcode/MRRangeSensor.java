package org.firstinspires.ftc.teamcode.core.testcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Date;

/**
 * Created by Adam.
 * November 04, 2016 at 4:13 PM
 */

@Autonomous(name = "MR Range Sensor", group = "Sensor")
public class MRRangeSensor extends LinearOpMode
{

    ModernRoboticsI2cRangeSensor rangeSensor;

    @Override public void runOpMode() throws InterruptedException
    {

        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range_sensor");

        waitForStart();

        while (opModeIsActive())
        {
            telemetry.addData("raw ultrasonic", rangeSensor.rawUltrasonic());
            telemetry.addData("raw optical", rangeSensor.rawOptical());
            telemetry.addData("cm optical", "%.2f cm", rangeSensor.cmOptical());
            telemetry.addData("cm", "%.2f cm", rangeSensor.getDistance(DistanceUnit.CM));
            telemetry.addData("", new Date());
            telemetry.update();
            idle();
        }
    }
}

