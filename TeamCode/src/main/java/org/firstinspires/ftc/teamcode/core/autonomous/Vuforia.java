package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.internal.VuforiaPoseMatrix;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam.
 * October 31, 2016 at 8:12 PM
 */

/* Vuforia */
// email: adamczuk@comcast.net
// password: 1983bestTeam

@TeleOp(name="Vuforia Navigation to Picture", group ="Concept")
public class Vuforia extends LinearOpMode
{
    public static final String TAG = "Vuforia Sample";

    String currentlyDriving = "";
    OpenGLMatrix lastLocation = null;
    MotorsHardware motors = new MotorsHardware();

    VuforiaLocalizer vuforia;

    @Override public void runOpMode() throws InterruptedException
    {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(com.qualcomm.ftcrobotcontroller.R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AcysFCv/////AAAAGU+6qxVe10kJlTULoOQAcrkzfvgmgt5PDrL+ho/I1mX/TXlXLGeylgmNqXRyYOyr+wg8zPWXuNUR8NPyXzSEly7gMrK93SOCY/q1VKlZA0CSoifvyW8+j+TgUJT5sf6yGdB9CUK669teYc2jEz75f7b61pnZpfgIRBEypVR0lHdgFNb0Y27rXzmwwXqCwNP/WYUjMgAI5R03d1BohQao1HjC8yf+ehw+lMZznENMBCSNrYiZfMR/r5Op68p+paOTxxL4ngXC8Im2WyHtsJvt3Y96G4M3Wdx89Slj/P2nZPBFbuya0In06K4egRMc5yfZiLBIWbo67M15Q6wIzYOi8aED8bDAylYoc/+9A8vnt78d";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

//        if(currentlyDriving.equals(""))
//        {
//            if(!motors.isInit())
//                motors.init(hardwareMap);
//
//            motors.setRightDirection(DcMotorSimple.Direction.FORWARD);
//            motors.setLeftPower(.22);
//            motors.setRightPower(.22);
//        }

        // Pictures
        VuforiaTrackables pictures = this.vuforia.loadTrackablesFromAsset("FTC_2016-17");
        VuforiaTrackable wheels = pictures.get(0);
        wheels.setName("Wheels");

        VuforiaTrackable tools  = pictures.get(1);
        tools.setName("Tools");

        VuforiaTrackable legos  = pictures.get(2);
        legos.setName("legos");

        VuforiaTrackable gears  = pictures.get(3);
        gears.setName("Gears");

        /** For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<>();
        allTrackables.addAll(pictures);

        /**
         * We use units of mm here because that's the recommended units of measurement for the
         * size values specified in the XML for the ImageTarget trackables in data sets. E.g.:
         *      <ImageTarget name="stones" size="247 173"/>
         * You don't *have to* use mm here, but the units here and the units used in the XML
         * target configuration files *must* correspond for the math to work out correctly.
         */
        float mmPerInch        = 25.4f;
        float mmBotWidth       = 18 * mmPerInch;            // ... or whatever is right for your robot
        float mmFTCFieldWidth  = (12*12 - 2) * mmPerInch;   // the FTC field is ~11'10" center-to-center of the glass panels


        OpenGLMatrix wheelsLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth/2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 90, 0));
        wheels.setLocation(wheelsLocationOnField);
        RobotLog.ii(TAG, "Wheels=%s", format(wheelsLocationOnField));

        OpenGLMatrix toolsLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the Blue Audience wall.
                Our translation here is a positive translation in Y.*/
                .translation(0, mmFTCFieldWidth/2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        tools.setLocation(toolsLocationOnField);
        RobotLog.ii(TAG, "Tools=%s", format(toolsLocationOnField));

        OpenGLMatrix legosLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the Blue Audience wall.
                Our translation here is a positive translation in Y.*/
                .translation(0, mmFTCFieldWidth/2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        legos.setLocation(legosLocationOnField);
        RobotLog.ii(TAG, "Legos=%s", format(legosLocationOnField));

        OpenGLMatrix gearsLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the Blue Audience wall.
                Our translation here is a positive translation in Y.*/
                .translation(0, mmFTCFieldWidth/2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        gears.setLocation(gearsLocationOnField);
        RobotLog.ii(TAG, "Gears=%s", format(gearsLocationOnField));

        /**
         * Create a transformation matrix describing where the phone is on the robot. Here, we
         * put the phone on the right hand side of the robot with the screen facing in (see our
         * choice of BACK camera above) and in landscape mode. Starting from alignment between the
         * robot's and phone's axes, this is a rotation of -90deg along the Y axis.
         *
         * When determining whether a rotation is positive or negative, consider yourself as looking
         * down the (positive) axis of rotation from the positive towards the origin. Positive rotations
         * are then CCW, and negative rotations CW. An example: consider looking down the positive Z
         * axis towards the origin. A positive rotation about Z (ie: a rotation parallel to the the X-Y
         * plane) is then CCW, as one would normally expect from the usual classic 2D geometry.
         */
        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(mmBotWidth/2,0,0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.YZY,
                        AngleUnit.DEGREES, -90, 0, 0));
        RobotLog.ii(TAG, "phone=%s", format(phoneLocationOnRobot));

        /**
         * Let the trackable listeners we care about know where the phone is. We know that each
         * listener is a {@link VuforiaTrackableDefaultListener} and can so safely cast because
         * we have not ourselves installed a listener of a different type.
         */
        ((VuforiaTrackableDefaultListener)wheels.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)tools.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)legos.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)gears.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);

        /**
         * A brief tutorial: here's how all the math is going to work:
         *
         * C = phoneLocationOnRobot  maps   phone coords -> robot coords
         * P = tracker.getPose()     maps   image target coords -> phone coords
         * L = redTargetLocationOnField maps   image target coords -> field coords
         *
         * So
         *
         * C.inverted()              maps   robot coords -> phone coords
         * P.inverted()              maps   phone coords -> imageTarget coords
         *
         * Putting that all together,
         *
         * L x P.inverted() x C.inverted() maps robot coords to field coords.
         *
         * @see VuforiaTrackableDefaultListener#getRobotLocation()
         */

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        /** Start tracking the data sets we care about. */
        pictures.activate();

        while(opModeIsActive())
        {
            for(VuforiaTrackable trackable : allTrackables)
            {
                /**
                 * getUpdatedRobotLocation() will return null if no new information is available since
                 * the last time that call was made, or if the trackable is not currently visible.
                 * getRobotLocation() will return null if the trackable is not currently visible.
                 */
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) trackable.getListener()).getPose();
                boolean visible = ((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible();
                telemetry.addData(trackable.getName(), visible ? "Visible" : "Not Visible");

                if(pose != null)
                {
                    VectorF translation = pose.getTranslation();

                    telemetry.addData(" - Translation", translation);

                    telemetry.addData(" - Degrees", Math.toDegrees(Math.atan2(translation.get(1), translation.get(2))));
                    //degrees to turn
                }

                //drive

                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null)
                    lastLocation = robotLocationTransform;
            }
            /**
             * Provide feedback as to where the robot was last located (if we know).
             */
            telemetry.addData("Position", lastLocation != null ? format(lastLocation) : "Unkown D:");

            telemetry.update();
            idle();
        }
    }

    /**
     * A simple utility that extracts positioning information from a transformation matrix
     * and formats it in a form palatable to a human being.
     */
    String format(OpenGLMatrix transformationMatrix)
    {
        return transformationMatrix.formatAsTransform();
    }

    private void drive(boolean visible, String name)
    {
       if(!motors.isInit())
           motors.init(hardwareMap);

        if(visible)
        {
            motors.resetLeftPower();
            motors.resetRightPower();
            motors.setLeftPower(.2);
            motors.setRightPower(.2);
            currentlyDriving = name;
        }

        else if(currentlyDriving.equals(name))
        {
            motors.resetLeftPower();
            motors.resetRightPower();
            currentlyDriving = "";
        }
    }
}

