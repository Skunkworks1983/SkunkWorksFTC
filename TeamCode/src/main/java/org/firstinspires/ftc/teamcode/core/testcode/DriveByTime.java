package org.firstinspires.ftc.teamcode.core.testcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.core.utils.MotorsHardware;

/**
 * Created by Adam.
 * November 18, 2016 at 3:27 PM
 */

@Autonomous(name="Drive by Time", group="idk")
@Disabled
public class DriveByTime extends LinearOpMode
{
    MotorsHardware robot = new MotorsHardware();
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException
    {
        robot.init(hardwareMap);

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        run(0.5, 0.5, 3);
        run(0.4, -0.4, 1.3);
        run(-0.5, 0.5, 1);

        robot.resetLeftPower();
        robot.resetRightPower();

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
        idle();
    }

    private void run(double rightPower, double leftPower, double seconds) throws InterruptedException
    {
        robot.setLeftPower(leftPower);
        robot.setRightPower(rightPower);
        runtime.reset();

        while(opModeIsActive() && (runtime.seconds() < seconds))
        {
            telemetry.addData("Path", "Leg #: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }
    }
}
