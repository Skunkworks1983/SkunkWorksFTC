package org.firstinspires.ftc.teamcode.core.autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
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
import org.firstinspires.ftc.teamcode.core.BaseOpMode;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;

import java.util.ArrayList;
import java.util.List;

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
    public double countsPerInch = (1120 * 0.7777778) / (4.0 * 3.1415);
    OpenGLMatrix lastLocation = null;
    MotorsHardware motors = new MotorsHardware();
    VuforiaLocalizer vuforia;
    ModernRoboticsI2cGyro gyro = null;
    double P_DRIVE_COEFF = 0.15;
    double P_TURN_COEFF = 0.1;
    static final double     HEADING_THRESHOLD       = 1;// As tight as we can make it with an integer gyro
    boolean gyros;


    public AutonomousEncoder()
    {
        gyros = false;
    }

    public AutonomousEncoder(boolean g)
    {
        gyros = g;
    }

    public abstract void encoders() throws InterruptedException;

    @Override
    public void runOpMode() throws InterruptedException
    {
        motors = new MotorsHardware();
        motors.init(hardwareMap);

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
        float mmBotWidth       = 18 * mmPerInch;            // ... or whatever is right for your motors
        float mmFTCFieldWidth  = (12*12 - 2) * mmPerInch;   // the FTC field is ~11'10" center-to-center of the glass panels


        OpenGLMatrix wheelsLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth/2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 90, 0));
        wheels.setLocation(wheelsLocationOnField);
        RobotLog.ii("Vuforia Sample", "Wheels=%s", format(wheelsLocationOnField));

        OpenGLMatrix toolsLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the Blue Audience wall.
                Our translation here is a positive translation in Y.*/
                .translation(0, mmFTCFieldWidth/2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        tools.setLocation(toolsLocationOnField);
        RobotLog.ii("Vuforia Sample", "Tools=%s", format(toolsLocationOnField));

        OpenGLMatrix legosLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the Blue Audience wall.
                Our translation here is a positive translation in Y.*/
                .translation(0, mmFTCFieldWidth/2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        legos.setLocation(legosLocationOnField);
        RobotLog.ii("Vuforia Sample", "Legos=%s", format(legosLocationOnField));

        OpenGLMatrix gearsLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the Blue Audience wall.
                Our translation here is a positive translation in Y.*/
                .translation(0, mmFTCFieldWidth/2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        gears.setLocation(gearsLocationOnField);
        RobotLog.ii("Vuforia Sample", "Gears=%s", format(gearsLocationOnField));

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
         */
        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(mmBotWidth/2,0,0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.YZY,
                        AngleUnit.DEGREES, -90, 0, 0));
        RobotLog.ii("Vuforia Sample", "phone=%s", format(phoneLocationOnRobot));

        /**
         * Let the trackable listeners we care about know where the phone is. We know that each
         * listener is a {@link VuforiaTrackableDefaultListener} and can so safely cast because
         * we have not ourselves installed a listener of a different type.
         */
        ((VuforiaTrackableDefaultListener)wheels.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)tools.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)legos.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)gears.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);

        if(gyros)
        {
            gyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");


            // Send telemetry message to alert driver that we are calibrating;
            telemetry.addData(">", "Calibrating Gyro");
            telemetry.update();

            int periods = 0;
            gyro.calibrate();

            // make sure the gyro is calibrated.
            while (gyro.isCalibrating())
            {
                Thread.sleep(50);
                while (gyro.isCalibrating())
                {
                    String period = "";
                    for (int i = 0; i <= periods; i++)
                        period += ".";
                    telemetry.addData(">", "Gyro Calibrating" + period + " Don't start!");
                    telemetry.update();
                    if (periods >= 4)
                        periods = 0;
                    else
                        periods++;
                    Thread.sleep(200);
                    idle();
                }
            }

            telemetry.addData(">", "Robot Ready.");
            telemetry.update();
        }

        motors.setLeftMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors.setRightMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        motors.setLeftMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motors.setRightMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d & %7d : %7d %7d",
                motors.leftMotor1.getCurrentPosition(),
                motors.leftMotor2.getCurrentPosition(),
                motors.rightMotor1.getCurrentPosition(),
                motors.rightMotor2.getCurrentPosition());
        telemetry.update();

        waitForStart();

        encoders();
        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
//        encoderDrive(DRIVE_SPEED,  48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
//        encoderDrive(TURN_SPEED,   12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
//        encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

        pictures.activate();

        while(opModeIsActive())
        {
            for(VuforiaTrackable trackable : allTrackables)
            {
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) trackable.getListener()).getPose();
                boolean visible = ((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible();
                telemetry.addData(trackable.getName(), visible ? "Visible" : "Not Visible");

                // If it's visible call a method or something

                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null)
                    lastLocation = robotLocationTransform;
            }
            /**
             * Provide feedback as to where the motors was last located (if we know).
             */
            telemetry.addData("Position", lastLocation != null ? format(lastLocation) : "Unknown D:");

            telemetry.update();
            idle();
        }
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
            sleep(100);
        }
    }

    public void gyroTurn(double speed, double angle) throws InterruptedException
    {

        // keep looping while we are still active, and not on heading.
        while (opModeIsActive() && !onHeading(speed, angle, P_TURN_COEFF))
        {
            // Update telemetry & Allow time for other processes to run.
            telemetry.update();
            idle();
        }
    }


    public void gyroHold(double speed, double angle, double holdTime) throws InterruptedException {

        ElapsedTime holdTimer = new ElapsedTime();

        // keep looping while we have time remaining.
        holdTimer.reset();
        while (opModeIsActive() && (holdTimer.time() < holdTime)) {
            // Update telemetry & Allow time for other processes to run.
            onHeading(speed, angle, P_TURN_COEFF);
            telemetry.update();
            idle();
        }

        // Stop all motion;
        motors.setLeftPower(0);
        motors.setRightPower(0);
    }


    boolean onHeading(double speed, double angle, double PCoeff) {
        double   error ;
        double   steer ;
        boolean  onTarget = false ;
        double leftSpeed;
        double rightSpeed;

        // determine turn power based on +/- error
        error = getError(angle);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            leftSpeed  = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        }
        else {
            steer = getSteer(error, PCoeff);
            rightSpeed  = speed * steer;
            leftSpeed   = -rightSpeed;
        }

        // Send desired speeds to motors.
        motors.setLeftPower(leftSpeed);
        motors.setRightPower(rightSpeed);

        // Display it for the driver.
        telemetry.addData("Target", "%5.2f", angle);
        telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
        telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);

        return onTarget;
    }

    /**
     * getError determines the error between the target angle and the robot's current heading
     * @param   targetAngle  Desired angle (relative to global reference established at last Gyro Reset).
     * @return  error angle: Degrees in the range +/- 180. Centered on the robot's frame of reference
     *          +ve error means the robot should turn LEFT (CCW) to reduce error.
     */
    public double getError(double targetAngle) {

        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = targetAngle - gyro.getIntegratedZValue();
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    /**
     * returns desired steering force.  +/- 1 range.  +ve = steer left
     * @param error   Error angle in robot relative degrees
     * @param PCoeff  Proportional Gain Coefficient
     * @return
     */
    public double getSteer(double error, double PCoeff) {
        return Range.clip(error * PCoeff, -1, 1);
    }

    public void tel(String msg)
    {
        telemetry.addData(msg, "");
        telemetry.update();
    }

    String format(OpenGLMatrix transformationMatrix)
    {
        return transformationMatrix.formatAsTransform();
    }
}
