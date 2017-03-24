package com.reminder;

import android.app.Application;
import android.content.Context;

/**
 * @author Bright. Create on 2017/3/24.
 */
public class MyApp extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }

    public static Context getAppContext() {
        return appContext;
    }
}
