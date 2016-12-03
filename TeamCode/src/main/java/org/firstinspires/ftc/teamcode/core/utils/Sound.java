package org.firstinspires.ftc.teamcode.core.utils;

import android.media.AudioManager;
import android.media.SoundPool;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.R;

/**
 * Created by Adam.
 * November 30, 2016 at 7:58 PM
 */

public class Sound
{
    private SoundPool mySound;
    private int beepID;
    private int explosionID;
    private int johnID;
    private int aidanID;
    private int nicoleID;
    private int rickrollID;
    private int awesomeID;
    private int bobID;

    public Sound(HardwareMap hardwareMap)
    {
        mySound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        beepID = mySound.load(hardwareMap.appContext, R.raw.beep, 1);
        explosionID = mySound.load(hardwareMap.appContext, R.raw.explosion, 1);
        johnID = mySound.load(hardwareMap.appContext, R.raw.john, 1);
        aidanID = mySound.load(hardwareMap.appContext, R.raw.aidan, 1);
        nicoleID = mySound.load(hardwareMap.appContext, R.raw.nicole, 1);
        rickrollID = mySound.load(hardwareMap.appContext, R.raw.rickroll, 1);
        awesomeID = mySound.load(hardwareMap.appContext, R.raw.awesome, 1);
        rickrollID = mySound.load(hardwareMap.appContext, R.raw.awesome, 1);
        bobID = mySound.load(hardwareMap.appContext, R.raw.bob, 1);
    }

    public void playBeep()
    {
        mySound.play(beepID, 1, 1, 1, 0, 1);
    }

    public void playExplosion()
    {
        mySound.play(explosionID, 1, 1, 1, 0, 1);
    }

    public void playJohn()
    {
        mySound.play(johnID, 1, 1, 1, 0, 1);
    }
    public void playNicole()
    {
        mySound.play(nicoleID, 1, 1, 1, 0, 1);
    }
    public void playAidan()
    {
        mySound.play(aidanID, 1, 1, 1, 0, 1);
    }

    public void playRickRoll()
    {
        mySound.play(rickrollID, 1, 1, 1, 0, 1);
    }

    public void playAwesome()
    {
        mySound.play(awesomeID, 1, 1, 1, 0, 1);
    }

    public void playBob()
    {
        mySound.play(bobID, 1, 1, 1, 0, 1);
    }
}
