package org.firstinspires.ftc.teamcode.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam.
 * December 02, 2016 at 3:40 PM
 */

public class Button
{
    private List<Boolean> buttonUp;
    private ButtonTask[] task;

    public Button(ButtonTask... task)
    {
        this.task = task;
        buttonUp = new ArrayList<>();

        for(int i = 0; i <= task.length; i++)
            buttonUp.add(true);
    }

    public void run(boolean... check)
    {
        int i = 0;
        for(ButtonTask cur : task)
        {
            if(check[i])
            {
                if(buttonUp.get(i))
                {
                    cur.run();
                    buttonUp.set(i, false);
                    return;
                }
            }

            else
                buttonUp.set(i, true);
        }
    }
}
