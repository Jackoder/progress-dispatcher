package com.jackoder.sample.app.observer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jackoder.progdispatcher.OnProgressListener;
import com.jackoder.progdispatcher.ProgressDispatcher;
import com.jackoder.sample.app.utils.LogFormater;

/**
 * @author Jackoder
 * @version 2016/9/29
 */
public class TargetIdService extends Service implements OnProgressListener {

    public static final String TAG = "TargetService";

    @Override
    public void onCreate() {
        super.onCreate();
        ProgressDispatcher.getInstance().addOnProgressListener(TAG, this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
