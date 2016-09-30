package com.jackoder.sample.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jackoder.progdispatcher.OnProgressListener;
import com.jackoder.progdispatcher.Progress;
import com.jackoder.progdispatcher.ProgressDispatcher;
import com.jackoder.sample.app.observer.NullIdService;
import com.jackoder.sample.app.observer.TargetIdBroadcastReceiver;
import com.jackoder.sample.app.observer.TargetIdService;
import com.jackoder.sample.app.utils.LogFormater;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author Jackoder
 * @version 2016/9/23
 */
public class MainActivity extends FragmentActivity implements OnProgressListener {

    @InjectView(R.id.tv_id)
    TextView mTvId;
    @InjectView(R.id.tv_progress)
    TextView mTvProgress;
    @InjectView(R.id.sb_progress)
    SeekBar  mSbProgress;
    @InjectView(R.id.tv_log)
    TextView mTvLog;

    private int mPosition;
    private String[] mItems = new String[]{"null", TargetIdBroadcastReceiver.TAG, TargetIdService.TAG};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this, this);
        mSbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTvProgress.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        ProgressDispatcher.getInstance().addOnProgressListener(null, this);

        startServices();
    }

    private void startServices() {
        startService(new Intent(this, NullIdService.class));
        startService(new Intent(this, TargetIdService.class));
    }

    @OnClick(R.id.tv_id)
    void showSingleChoiceButton() {
        new AlertDialog.Builder(this)
                .setTitle("Target Id")
                .setSingleChoiceItems(mItems, mPosition, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPosition = which;
                        mTvId.setText("" + mItems[mPosition]);
                    }
                }).show();
    }

    @OnClick(R.id.btn_dispatch)
    void dispatch() {
        Progress progress = new Progress(getId());
        progress.setProgress(getProgress());
        ProgressDispatcher.getInstance().getProgressObserver(getId()).onNext(progress);
    }

    @OnClick(R.id.btn_error)
    void error() {
        Exception exception = new Exception("Custom error.");
        ProgressDispatcher.getInstance().getProgressObserver(getId()).onError(exception);
    }

    @OnClick(R.id.btn_complete)
    void completed() {
        ProgressDispatcher.getInstance().getProgressObserver(getId()).onCompleted();
    }

    public String getId() {
        return mItems[mPosition].equals("null") ? null : mItems[mPosition];
    }

    public int getProgress() {
        return mSbProgress.getProgress();
    }

    @Override
    public void onProgress(String id, int progress, Object context) {
        mTvLog.setText(LogFormater.format(id, progress, context) + "\n" + mTvLog.getText().toString());
    }

    @Override
    public void onError(String id, Throwable throwable) {
        mTvLog.setText(LogFormater.format(id, throwable) + "\n" + mTvLog.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProgressDispatcher.getInstance().release();
    }
}
