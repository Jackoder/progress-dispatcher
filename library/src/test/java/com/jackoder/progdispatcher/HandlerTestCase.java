package com.jackoder.progdispatcher;

import android.os.Handler;
import android.os.Looper;

import com.jackoder.progdispatcher.base.BaseTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author Jackoder
 * @version 2016/9/29
 */
public class HandlerTestCase extends BaseTestCase {

    private static final String ID = "id";

    String mThreadName;

    @Test
    public void testDefaultHandler() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
//        ProgressDispatcher.init(null);
        ProgressDispatcher.getInstance().addOnProgressListener(null, new OnProgressListener() {
            @Override
            public void onProgress(String id, int progress, Object context) {
                mThreadName = Thread.currentThread().getName();
                countDownLatch.countDown();
            }

            @Override
            public void onError(String id, Throwable throwable) {

            }
        });
        ProgressDispatcher.getInstance().getProgressObserver(ID).onNext(new Progress(ID));
        try {
            countDownLatch.await();
            Assert.assertEquals("main", mThreadName);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testTargetHandler() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ProgressDispatcher.init(new Handler(Looper.myLooper()));
                ProgressDispatcher.getInstance().addOnProgressListener(null, new OnProgressListener() {
                    @Override
                    public void onProgress(String id, int progress, Object context) {
                        mThreadName = Thread.currentThread().getName();
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onError(String id, Throwable throwable) {

                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ProgressDispatcher.getInstance().getProgressObserver(ID).onNext(new Progress(ID));
            }
        }, "test").start();
        try {
            countDownLatch.await();
            Assert.assertEquals("test", mThreadName);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Before
    public void tearDown() throws Exception {
        ProgressDispatcher.getInstance().release();
    }
}
