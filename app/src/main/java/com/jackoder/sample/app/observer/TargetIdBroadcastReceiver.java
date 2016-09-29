package com.jackoder.sample.app.observer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jackoder.progdispatcher.OnProgressListener;
import com.jackoder.progdispatcher.ProgressDispatcher;
import com.jackoder.sample.app.utils.LogFormater;

/**
 * @author Jackoder
 * @version 2016/9/29
 */
public class TargetIdBroadcastReceiver extends BroadcastReceiver implements OnProgressListener {

    public static final String TAG = "TargetBroadcastReceiver";

    public TargetIdBroadcastReceiver() {
        ProgressDispatcher.getInstance().addOnProgressListener(TAG, this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    @Override
    public void onProgress(String id, int progress, Object context) {
        Log.i(TAG, LogFormater.format(id, progress, context));
    }

    @Override
    public void onError(String id, Throwable throwable) {
        Log.e(TAG, LogFormater.format(id, throwable));
    }
}
