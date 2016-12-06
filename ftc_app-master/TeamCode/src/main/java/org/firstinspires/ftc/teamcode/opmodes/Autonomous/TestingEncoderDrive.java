package org.firstinspires.ftc.teamcode.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s-4041730 on 12/2/2016.
 */

@Autonomous(name="encoderdrivinghple")
public class TestingEncoderDrive extends ConceptVuforiaNavigation {

    //gyro and drive
    double DRIVE_SPEED = 0.75;
    private ElapsedTime runtime = new ElapsedTime(); //put this in every thing which you want a runtime
    int target = 60;

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap);

        vuforiaInit();

        resetEncoders();
        runUsingEncoders();
        encoderDrive(DRIVE_SPEED, 10, 10, 5.0);

        runWithoutEncoders();
        turnAbsolute(target);

        while (opModeIsActive()){

            for (VuforiaTrackable trackable : allTrackables) {
                /**
                 * getUpdatedRobotLocation() will return null if no new information is available since
                 * the last time that call was made, or if the trackable is not currently visible.
                 * getRobotLocation() will return null if the trackable is not currently visible.
                 * */
                telemetry.addData(trackable.getName(), ((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible() ? "Visible" : "Not Visible");    //

                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
            }
            /**
             * Provide feedback as to where the robot was last located (if we know).
             */
            if (lastLocation != null) {
                //  RobotLog.vv(TAG, "robot=%s", format(lastLocation));
                float[] robotLocationArray = lastLocation.getData();
                x = robotLocationArray[12];
                y = robotLocationArray[13];
                z = robotLocationArray[14];

                rot = Orientation.getOrientation(lastLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                robotBearing = rot.thirdAngle;

                telemetry.addData("Location", "X: " + x + " " + "Y: " + y);
                telemetry.addData("Rotation", robotBearing);

                } else {
                    telemetry.addData("Pos", "Unknown");
                }

            telemetry.update();

        }
    }
}
