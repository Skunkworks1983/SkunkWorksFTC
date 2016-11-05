package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by s-4041730 on 11/5/2016.
 */

public class Base {
    public DcMotor MotorFL   = null;
    public DcMotor MotorFR  = null;
    public DcMotor MotorBL    = null;
    public DcMotor MotorBR = null;

    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    public Base(){

    }

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        MotorFL  = hwMap.dcMotor.get("leftFront");
        MotorFR = hwMap.dcMotor.get("rightFront");
        MotorBL = hwMap.dcMotor.get("leftBack");
        MotorBR = hwMap.dcMotor.get("rightBack");

        MotorFL.setDirection(DcMotor.Direction.REVERSE);
        MotorBL.setDirection(DcMotor.Direction.REVERSE);
        MotorFR.setDirection(DcMotor.Direction.FORWARD);
        MotorBR.setDirection(DcMotor.Direction.FORWARD);


        // Set all motors to zero power
        MotorFL.setPower(0);
        MotorBL.setPower(0);
        MotorFR.setPower(0);
        MotorBR.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        MotorFL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorBL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorFR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MotorBR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

}
