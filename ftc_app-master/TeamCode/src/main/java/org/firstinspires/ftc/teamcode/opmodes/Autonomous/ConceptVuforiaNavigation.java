/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode.opmodes.Autonomous;

import android.graphics.Path;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
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
import org.firstinspires.ftc.teamcode.opmodes.Base;
import org.firstinspires.ftc.teamcode.opmodes.Teleop.GyroTesting;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Autonomous(name="XYZ + Rot Vuforia")

public abstract class ConceptVuforiaNavigation extends Base {

    public static final String TAG = "Vuforia Sample";

    public float x;
    public float y;
    public float z;
    public float robotBearing;
    public Orientation rot;
    public OpenGLMatrix lastLocation = null;
    public VuforiaLocalizer vuforia;
    public List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();


    public void vuforiaInit() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AZkqQkH/////AAAAGdiGT5c3sUI4rBa3mJU4jt18f+jlyz2znmOo2EiHBSTv77Q5ujdYwemMmLblk51L+dDswjbz3BwJtlNdupU7ee5LMwuE+pRmJhJSTfzmHsL5+z9enLQv88uQj7yGuy1WCfKDQS7lNh6FMlRoswyIfH3aPc9ncQFgk4Hk22gOosnpA6ugrbrqKzg802X4INkGq/ozNtt/RdR/xW0KfMFNRpiNX5VwvjV6mgx2i6XuRfAemjeCmcansRUsdpy54RmrwdH57krn48/L9xAouVvNK+6Boq8PXo+OB0jTngIo0JCWmr58T7qwW2b27EL6FSdoOFbd94hjzfnRvZilROP9IwQULyYgbqKZWyAvym/dzwYT";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");

        VuforiaTrackable wheels = beacons.get(0);
        VuforiaTrackable tools = beacons.get(1);
        VuforiaTrackable lego = beacons.get(2);
        VuforiaTrackable gears = beacons.get(3);

        wheels.setName("Wheels");
        tools.setName("Tools");
        lego.setName("Lego");
        gears.setName("Gears");

        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(beacons);

        float mmPerInch        = 25.4f;
        float mmBotWidth       = 17 * mmPerInch;            // ... or whatever is right for your robot
        float mmFTCFieldWidth  = (12*12 - 2) * mmPerInch;   // the FTC field is ~11'10" center-to-center of the glass panels

        OpenGLMatrix wheelsTargetLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the RED WALL. Our translation here
                is a negative translation in X.*/
                .translation(mmFTCFieldWidth/300, mmFTCFieldWidth/1790, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X, then 90 in Z */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, -90, 0));
        wheels.setLocation(wheelsTargetLocationOnField);
        RobotLog.ii(TAG, "Wheels Target=%s", format(wheelsTargetLocationOnField));

       /*
        * To place the Stones Target on the Blue Audience wall:
        * - First we rotate it 90 around the field's X axis to flip it upright
        * - Finally, we translate it along the Y axis towards the blue audience wall.
        */
        OpenGLMatrix toolsTargetLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the Blue Audience wall.
                Our translation here is a positive translation in Y.*/
                .translation(-mmFTCFieldWidth/1790, mmFTCFieldWidth/900, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, -90, 0));
        tools.setLocation(toolsTargetLocationOnField);
        RobotLog.ii(TAG, "Tools Target=%s", format(toolsTargetLocationOnField));

        OpenGLMatrix legoTargetLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the RED WALL. Our translation here
                is a negative translation in X.*/
                .translation(-mmFTCFieldWidth/900, mmFTCFieldWidth/1790, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X, then 90 in Z */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 0, -90, 0));
        lego.setLocation(legoTargetLocationOnField);
        RobotLog.ii(TAG, "Lego Target=%s", format(legoTargetLocationOnField));

       /*
        * To place the Stones Target on the Blue Audience wall:
        * - First we rotate it 90 around the field's X axis to flip it upright
        * - Finally, we translate it along the Y axis towards the blue audience wall.
        */
        OpenGLMatrix gearsTargetLocationOnField = OpenGLMatrix
                /* Then we translate the target off to the Blue Audience wall.
                Our translation here is a positive translation in Y.*/
                .translation(-mmFTCFieldWidth/1790, -mmFTCFieldWidth/300, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 0, -90, 0));
        gears.setLocation(gearsTargetLocationOnField);
        RobotLog.ii(TAG, "Gears Target=%s", format(gearsTargetLocationOnField));

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(mmBotWidth/2,0,0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.YZY,
                        AngleUnit.DEGREES, -90, 0, 0));
        RobotLog.ii(TAG, "phone=%s", format(phoneLocationOnRobot));

        ((VuforiaTrackableDefaultListener)wheels.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)tools.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)lego.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
        ((VuforiaTrackableDefaultListener)gears.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();

        beacons.activate();
    }

    /**
     * A simple utility that extracts positioning information from a transformation matrix
     * and formats it in a form palatable to a human being.
     */
    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }

}