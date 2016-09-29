package com.jackoder.sample.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * @author Jackoder
 * @version 2016/9/29
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
