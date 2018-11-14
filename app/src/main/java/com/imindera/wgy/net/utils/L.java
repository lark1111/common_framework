package com.imindera.wgy.net.utils;

import android.util.Log;

/**
 * Created by zhouyu on 2018/9/4.
 */
public class L
{
    private static boolean debug = false;

    public static void e(String msg)
    {
        if (debug)
        {
            Log.e("OkHttp", msg);
        }
    }

}

