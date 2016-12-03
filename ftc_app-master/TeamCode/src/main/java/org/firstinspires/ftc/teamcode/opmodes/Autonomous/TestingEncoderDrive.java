package org.firstinspires.ftc.teamcode.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.Base;

/**
 * Created by s-4041730 on 12/2/2016.
 */

@Autonomous(name="encoderdrivinghple")
public class TestingEncoderDrive extends Base{

    double DRIVE_SPEED = 0.75;

    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            encoderDrive(DRIVE_SPEED, 10, 10, 5.0);
        }
    }
}
