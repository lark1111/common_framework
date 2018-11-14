package com.imindera.wgy.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    /**
     * @param context
     * @param properties 文件名
     * @return
     */
    public static Properties getProperties(Context context, String properties) {
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = context.getAssets().open(properties);
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
