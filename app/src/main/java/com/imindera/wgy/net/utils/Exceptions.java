package com.imindera.wgy.net.utils;

/**
 * Created by zhouyu on 2018/9/4.
 */
public class Exceptions
{
    public static void illegalArgument(String msg, Object... params)
    {
        throw new IllegalArgumentException(String.format(msg, params));
    }


}
