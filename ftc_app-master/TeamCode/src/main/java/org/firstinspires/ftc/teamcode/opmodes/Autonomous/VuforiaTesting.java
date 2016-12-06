package org.firstinspires.ftc.teamcode.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.teamcode.opmodes.Base;

/**
 * Created by s-4041730 on 12/5/2016.
 */

@TeleOp(name = "memedreamfighingmachine")
public class VuforiaTesting extends Base {

    double DRIVE_SPEED = 0.25;

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap);


        resetEncoders();
        runUsingEncoders();

        waitForStart();

        encoderDrive(DRIVE_SPEED, 10.0, 10.0, 10.0);

    }
}
