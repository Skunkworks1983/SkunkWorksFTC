package org.firstinspires.ftc.teamcode.opmodes.Autonomous;

import android.os.SystemClock;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import android.media.MediaPlayer;

import org.firstinspires.ftc.teamcode.R;

@Autonomous(name="Drive In Circle", group="Linear Opmode")

public class ExampleDriveInCircle extends OpMode {
    DcMotor leftMotor;
    DcMotor rightMotor;

  @Override
  public void init() {
//      MediaPlayer mediaPlayer = MediaPlayer.create(org.firstinspires.ftc.teamcode., R.raw.tada);
//      mediaPlayer.start();
//      I'll come back to this later...

      leftMotor = hardwareMap.dcMotor.get("left_drive");
      rightMotor = hardwareMap.dcMotor.get("right_drive");

      leftMotor.setDirection(DcMotor.Direction.REVERSE);
      rightMotor.setDirection(DcMotor.Direction.FORWARD);



  }

  @Override
  public void loop() {
      leftMotor.setPower(0.25);
      rightMotor.setPower(0.25);
      SystemClock.sleep(1000);
      leftMotor.setPower(0.0);
      rightMotor.setPower(0.75);
      SystemClock.sleep(1000);
      leftMotor.setPower(0.25);
      rightMotor.setPower(0.25);
      SystemClock.sleep(1000);
      leftMotor.setPower(0.75);
      rightMotor.setPower(0.0);
      SystemClock.sleep(1000);
  }
}
