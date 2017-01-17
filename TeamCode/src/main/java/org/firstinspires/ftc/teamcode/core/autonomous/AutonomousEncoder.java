package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.core.BaseOpMode;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;


/**
 * Created by Adam.
 * December 02, 2016 at 4:12 PM
 */

/*
    Red wire on outside, yellow inside twrds plugs
 */

public abstract class AutonomousEncoder extends BaseOpMode
{
    private ElapsedTime runtime = new ElapsedTime();
    public double countsPerInch = (1120 * .5) / (3.875 * 3.1415);
//    public double countsPerInch = (1120 * 0.7777778) / (4.0 * 3.1415);
    OpenGLMatrix lastLocation = null;
    MotorsHardware motors = new MotorsHardware();
    VuforiaTrackables beacons;

    public abstract void encoders() throws InterruptedException;

    @Override
    public void runOpMode() throws InterruptedException
    {
        tel("Setting up...");
        motors = new MotorsHardware();
        motors.init(hardwareMap);

        motors.setLeftMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors.setRightMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        motors.setLeftMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motors.setRightMode(DcMotor.RunMode.RUN_USING_ENCODER);

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(com.qualcomm.ftcrobotcontroller.R.id.cameraMonitorViewId);
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.vuforiaLicenseKey = "AcysFCv/////AAAAGU+6qxVe10kJlTULoOQAcrkzfvgmgt5PDrL+ho/I1mX/TXlXLGeylgmNqXRyYOyr+wg8zPWXuNUR8NPyXzSEly7gMrK93SOCY/q1VKlZA0CSoifvyW8+j+TgUJT5sf6yGdB9CUK669teYc2jEz75f7b61pnZpfgIRBEypVR0lHdgFNb0Y27rXzmwwXqCwNP/WYUjMgAI5R03d1BohQao1HjC8yf+ehw+lMZznENMBCSNrYiZfMR/r5Op68p+paOTxxL4ngXC8Im2WyHtsJvt3Y96G4M3Wdx89Slj/P2nZPBFbuya0In06K4egRMc5yfZiLBIWbo67M15Q6wIzYOi8aED8bDAylYoc/+9A8vnt78d";
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);   // four targets on the field

        beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");

        tel("Ready to start!");
        waitForStart();
        encoders();
    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) throws InterruptedException
    {
        int newLeftTarget1;
        int newLeftTarget2;
        int newRightTarget1;
        int newRightTarget2;

        if(opModeIsActive())
        {
            // Determine new target position, and pass to motor controller
            newLeftTarget1 = motors.leftMotor1.getCurrentPosition() + (int)(leftInches * countsPerInch);
            newLeftTarget2 = motors.leftMotor2.getCurrentPosition() + (int)(leftInches * countsPerInch);
            newRightTarget1 = motors.rightMotor1.getCurrentPosition() + (int)(rightInches * countsPerInch);
            newRightTarget2 = motors.rightMotor2.getCurrentPosition() + (int)(rightInches * countsPerInch);
            motors.leftMotor1.setTargetPosition(newLeftTarget1);
            motors.leftMotor2.setTargetPosition(newLeftTarget2);
            motors.rightMotor1.setTargetPosition(newRightTarget1);
            motors.rightMotor2.setTargetPosition(newRightTarget2);

            motors.setLeftMode(DcMotor.RunMode.RUN_TO_POSITION);
            motors.setRightMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            motors.setLeftPower(Math.abs(speed));
            motors.setRightPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (motors.leftMotor1.isBusy() && motors.leftMotor2.isBusy() && motors.rightMotor1.isBusy() && motors.rightMotor2.isBusy()))
            {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget1,  newRightTarget1);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        motors.leftMotor1.getCurrentPosition(),
                        motors.rightMotor2.getCurrentPosition());
                telemetry.update();
            }

            motors.setLeftPower(0);
            motors.setRightPower(0);

            motors.setLeftMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motors.setRightMode(DcMotor.RunMode.RUN_USING_ENCODER);

            tel("Finished");
        }
    }

    public void run(double rightPower, double leftPower, double seconds) throws InterruptedException
    {
        motors.setLeftPower(leftPower);
        motors.setRightPower(rightPower);
        runtime.reset();

        while(opModeIsActive())
        {
            if(runtime.seconds() > seconds)
            {
                motors.setLeftPower(0);
                motors.setRightPower(0);
                sleep(150);
                break;
            }

            idle();
        }
    }

    String format(OpenGLMatrix transformationMatrix)
    {
        return transformationMatrix.formatAsTransform();
    }

    /*
        float mmPerInch        = 25.4f;
        float mmBotWidth       = 18 * mmPerInch;            // ... or whatever is right for your motors
        float mmFTCFieldWidth  = (12*12 - 2) * mmPerInch;   // the FTC field is ~11'10" center-to-center of the glass panels


        /**
         * Create a transformation matrix describing where the phone is on the motors. Here, we
         * put the phone on the right hand side of the motors with the screen facing in (see our
         * choice of BACK camera above) and in landscape mode. Starting from alignment between the
         * motors's and phone's axes, this is a rotation of -90deg along the Y axis.
         *
         * When determining whether a rotation is positive or negative, consider yourself as looking
         * down the (positive) axis of rotation from the positive towards the origin. Positive rotations
         * are then CCW, and negative rotations CW. An example: consider looking down the positive Z
         * axis towards the origin. A positive rotation about Z (ie: a rotation parallel to the the X-Y
         * plane) is then CCW, as one would normally expect from the usual classic 2D geometry.

    OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
            .translation(mmBotWidth/2,0,0)
            .multiplied(Orientation.getRotationMatrix(
                    AxesReference.EXTRINSIC, AxesOrder.YZY,
                    AngleUnit.DEGREES, -90, 0, 0));
    RobotLog.ii("Vuforia Sample", "phone=%s", format(phoneLocationOnRobot));
    */
}