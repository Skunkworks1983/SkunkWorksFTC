package org.firstinspires.ftc.teamcode.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmodes.Base;

/**
 * Created by s-4041730 on 12/11/2016.
 */

@Autonomous(name = "Reset Everything")
public class resetEverything extends Base {
    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap);
        ballRelease.setPosition(0);
        sleep(CYCLE_MS);

    }
}
