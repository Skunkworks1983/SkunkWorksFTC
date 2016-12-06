package org.firstinspires.ftc.teamcode.opmodes.VeryGarbage;

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
    private int desiinerID;

    public Sound(HardwareMap hardwareMap)
    {
        mySound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        beepID = mySound.load(hardwareMap.appContext, R.raw.bleepbloop, 1);
        desiinerID = mySound.load(hardwareMap.appContext, R.raw.desiiner, 1);
    }

    public void playBeep()
    {
        mySound.play(beepID, 1, 1, 1, 0, 1);
    }

    public void playDesiiner()
    {
        mySound.play(desiinerID, 1, 1, 1, 0, 1);
    }
}