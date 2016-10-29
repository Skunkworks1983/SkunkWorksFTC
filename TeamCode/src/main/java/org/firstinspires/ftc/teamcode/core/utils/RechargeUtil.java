package org.firstinspires.ftc.teamcode.core.utils;

import java.util.HashMap;

/**
 * Created by Adam.
 * October 29, 2016 at 11:21 AM
 */

public class RechargeUtil
{
    private static HashMap<String,  Long> recharge = new HashMap<>();

    public static void recharge(String id, long millis)
    {
        recharge.put(id, System.currentTimeMillis() + millis);
    }

    public static boolean canUse(String id)
    {
        if(recharge.containsKey(id))
        {
            if(recharge.get(id) > System.currentTimeMillis())
                return false;

            recharge.remove(id);
        }
        return true;
    }

}
