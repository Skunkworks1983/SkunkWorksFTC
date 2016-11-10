package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by s-4041693 on 11/2/2016.
 */
@Autonomous(name = "180", group = "180")
public class OneEighty extends LinearOpMode{
    DcMotor leftMotor1;
    DcMotor rightMotor1;
    DcMotor leftMotor2;
    DcMotor rightMotor2;

    @Override
    public void runOpMode() throws InterruptedException {
        //setup the left and right motors from the configuration file
        leftMotor1 = hardwareMap.dcMotor.get("left_drive1");
        leftMotor2 = hardwareMap.dcMotor.get("left_drive2");
        rightMotor1 = hardwareMap.dcMotor.get("right_drive1");
        rightMotor2 = hardwareMap.dcMotor.get("right_drive2");

        waitForStart();

        //Stop the robot
        leftMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor2.setDirection(DcMotorSimple.Direction.REVERSE);

        leftMotor1.setPower(0.25);
        rightMotor1.setPower(0.25);
        leftMotor2.setPower(0.25);
        rightMotor2.setPower(0.25);
        sleep(6000);
    }

}
