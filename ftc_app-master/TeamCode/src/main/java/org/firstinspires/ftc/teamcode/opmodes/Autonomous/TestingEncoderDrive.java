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
    double timeoutS = 5.0;

    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap);

        MotorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MotorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        MotorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        encoderDrive(DRIVE_SPEED, 10, 10, 5.0);

        while (opModeIsActive()){

            while ((runtime.seconds() < timeoutS) &&
                    (MotorFL.isBusy() && MotorFR.isBusy() && MotorBL.isBusy() && MotorBR.isBusy())){
            //telemetry.addData("Path1",  "Running to %7d :%7d", newFLTarget,  newFRTarget);
            telemetry.addData("Path2",  "Running at %7d :%7d",
                    MotorFL.getCurrentPosition(),
                    MotorFR.getCurrentPosition(),
                    MotorBL.getCurrentPosition(),
                    MotorBR.getCurrentPosition());
            telemetry.update();
            }
        }
    }
}
