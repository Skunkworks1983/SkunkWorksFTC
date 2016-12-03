package org.firstinspires.ftc.teamcode.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.teamcode.opmodes.Base;

/**
 * Created by s-4041730 on 12/2/2016.
 */

@Autonomous(name="encoderdrivinghple")
public class TestingEncoderDrive extends ConceptVuforiaNavigation{

    double DRIVE_SPEED = 0.75;
    private ElapsedTime runtime = new ElapsedTime(); //put this in every thing which you want a runtime
    int target = -45;

    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);
        vuforiaInit();

        waitForStart();

        resetEncoders();
        runUsingEncoders();
        encoderDrive(DRIVE_SPEED, 10, 10, 5.0);

        while (opModeIsActive()){

            for (VuforiaTrackable trackable : allTrackables) {

                telemetry.addData(trackable.getName(), ((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible() ? "Visible" : "Not Visible");

                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
            }

            if (lastLocation != null) {
                //  RobotLog.vv(TAG, "robot=%s", format(lastLocation));

                float[] robotLocationArray = lastLocation.getData();
                x = robotLocationArray[12];
                y = robotLocationArray[13];
                z = robotLocationArray[14];

                rot = Orientation.getOrientation(lastLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                robotBearing = rot.thirdAngle;
            }

            telemetry.addData("Turning", "");
            telemetry.addData("X", x + "Y", y);
            telemetry.addData("Rotation", robotBearing);


            runWithoutEncoders();
            turnAbsolute(target);
            telemetry.update();

        }
    }
}
