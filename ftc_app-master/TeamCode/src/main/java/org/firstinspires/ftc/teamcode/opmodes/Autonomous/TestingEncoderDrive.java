package org.firstinspires.ftc.teamcode.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.opmodes.Base;

/**
 * Created by s-4041730 on 12/2/2016.
 */

@Autonomous(name="encoderdrivinghple")
public class TestingEncoderDrive extends Base{

    double DRIVE_SPEED = 0.75;
    private ElapsedTime runtime = new ElapsedTime(); //put this in every thing which you want a runtime
    int target = -45;

    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);

        waitForStart();

        resetEncoders();
        runUsingEncoders();
        encoderDrive(DRIVE_SPEED, 10, 10, 5.0);

        sleep(1000); //wait before turning

        runWithoutEncoders();
        turnAbsolute(target);

        while (opModeIsActive()){
            telemetry.addData("title", "it works");
            telemetry.update();
            }
    }
}
