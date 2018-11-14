package com.imindera.wgy.base;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import org.litepal.LitePalApplication;

/**
 * Created by zhouyu on 2018/9/3.
 */

public class WGYApplication extends LitePalApplication {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        CrashReport.initCrashReport(getApplicationContext(), "74a0e4a4b3", true);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
