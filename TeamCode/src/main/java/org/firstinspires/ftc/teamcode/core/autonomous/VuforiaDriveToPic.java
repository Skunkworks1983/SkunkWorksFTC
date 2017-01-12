package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.core.BaseOpMode;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;

/**
 * Created by Adam.
 * January 11, 2017 at 4:04 PM
 */

@Autonomous(name = "Vufoira Drive to Picture", group = "Vuforia")
public class VuforiaDriveToPic extends BaseOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        motors = new MotorsHardware();
        motors.init(hardwareMap);
        double left = 0;
        double right = 0;

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(com.qualcomm.ftcrobotcontroller.R.id.cameraMonitorViewId);
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.vuforiaLicenseKey = "AcysFCv/////AAAAGU+6qxVe10kJlTULoOQAcrkzfvgmgt5PDrL+ho/I1mX/TXlXLGeylgmNqXRyYOyr+wg8zPWXuNUR8NPyXzSEly7gMrK93SOCY/q1VKlZA0CSoifvyW8+j+TgUJT5sf6yGdB9CUK669teYc2jEz75f7b61pnZpfgIRBEypVR0lHdgFNb0Y27rXzmwwXqCwNP/WYUjMgAI5R03d1BohQao1HjC8yf+ehw+lMZznENMBCSNrYiZfMR/r5Op68p+paOTxxL4ngXC8Im2WyHtsJvt3Y96G4M3Wdx89Slj/P2nZPBFbuya0In06K4egRMc5yfZiLBIWbo67M15Q6wIzYOi8aED8bDAylYoc/+9A8vnt78d";
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);   // four targets on the field

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");

        VuforiaTrackable beacon = beacons.get(3);   // gears

        waitForStart();

        beacons.activate();

        while(opModeIsActive())
        {

            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beacon.getListener()).getPose();

            if(pose != null)
            {
                telemetry.addData(beacon.getName() + "is visible?", ((VuforiaTrackableDefaultListener) beacon.getListener()).isVisible());

                VectorF translation = pose.getTranslation();
                //telemetry.addData(beacon.getName() + "-Translation", translation); // format(pose) shows both orientation and translation
                //telemetry.addData(beacon.getName()+"-vector", pose.toVector());    // dump entire matrix

                telemetry.addData("Pose", format(pose));
                    /* based on observation of pose, it represents the position and orientation of the target image.
                       The position is in milimeters, the orientation is of the target, not the phone.
                       eg. if the phone and target are both parallel to the floor, but the phone is offset towards one edge of the target
                       and is above the floor,
                       then the orientation is (0,0,0) but the translation will contain an offset from the centre of the target,
                       in both the vertical axis and in the other axis.
                       It's that offset that can be used to calculate the angle from the phone to the target.
                     */

                double angle = Math.atan2(translation.get(2), translation.get(0)); // in radians
                double degreesToTurn = Math.toDegrees(angle) + 90;                 // adjust for vertical orientation of phone
                telemetry.addData(beacon.getName() + " Degrees", degreesToTurn);
                double distance = Math.sqrt(translation.get(2)*translation.get(2) + translation.get(0)*translation.get(0));  // Pythagoras calc of hypotenuse
                telemetry.addData(beacon.getName() + " Distance", distance);


                if(distance < 200)
                {
                    left = 0;
                    right = 0;
                }

                else if (degreesToTurn > 10) //turn right
                {
                    left = -0.2;
                    right = -0.1;
                }

                else if (degreesToTurn < -10)  //turn left
                {
                    left = -0.1;
                    right = -0.2;
                }

                else // go straight
                {
                    left = -0.2;
                    right = -0.2;
                }

                motors.setLeftPower(left);
                motors.setRightPower(right);
            }

            telemetry.update();
            idle();
        }
    }

    String format(OpenGLMatrix transformationMatrix)
    {
        return transformationMatrix.formatAsTransform();
    }
}

