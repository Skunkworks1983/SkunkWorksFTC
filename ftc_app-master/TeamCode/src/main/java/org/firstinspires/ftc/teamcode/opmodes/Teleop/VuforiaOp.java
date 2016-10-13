package org.firstinspires.ftc.teamcode.opmodes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;


public class VuforiaOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(0).setName("Tools");
        beacons.get(0).setName("Lego");
        beacons.get(0).setName("Gears");

        waitForStart();

        beacons.activate();
        while(opModeIsActive()) {
            for(VuforiaTrackable beac : beacons ) {
                OpenGLMatrix pose =((VuforiaTrackableDefaultListener) beac.getListener()).getPose();

                if(pose !=null){
                    VectorF translation = pose.getTranslation();

                    telemetry.addData(beac.getName() + "~Translation", translation);

                    double degreesToTurn = Math.toDegrees(Math.atan2(translation.get(1). translation.get(2)));

                    telemetry.addData(beac.getName() +"~Degrees", degreesToTurn);
                }
            }
            telemetry.update();
        }
    }