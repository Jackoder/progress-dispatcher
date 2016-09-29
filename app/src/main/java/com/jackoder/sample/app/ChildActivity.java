package com.jackoder.sample.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jackoder.progdispatcher.OnProgressListener;
import com.jackoder.progdispatcher.ProgressDispatcher;

/**
 * @author Jackoder
 * @version 2016/9/29
 */
public class ChildActivity extends AppCompatActivity implements OnProgressListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressDispatcher.getInstance().addOnProgressListener(null, this);
    }

    @Override
    public void onProgress(String id, int progress, Object context) {

    }

    @Override
    public void onError(String id, Throwable throwable) {

    }
}
