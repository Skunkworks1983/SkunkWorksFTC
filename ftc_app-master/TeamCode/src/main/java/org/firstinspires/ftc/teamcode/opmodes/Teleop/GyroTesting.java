package org.firstinspires.ftc.teamcode.opmodes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.opmodes.Base;

//import org.firstinspires.ftc.teamcode.LBHW;

@TeleOp(name="Gyro", group="")
public class GyroTesting extends Base {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap);
        runWithoutEncoders();
        calibrateGyro();

        int mem = 0;  //Desired angle to turn to

        while (mrGyro.isCalibrating()) {
            idle();
        }

        telemetry.addData(">", "Gyro is calibrated");
        telemetry.update();
        waitForStart();  //Wait for play button to be pressed

        while (opModeIsActive()) {
            if (gamepad1.a) {
                mem = mem + 45;
            }
            if (gamepad1.b) {
                mem = mem - 45;
            }
            telemetry.addData(">", mem);
            telemetry.update();
            //turnAbsolute(mem);

            idle();
        }
    }
}