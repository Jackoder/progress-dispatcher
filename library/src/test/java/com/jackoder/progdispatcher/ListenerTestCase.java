package com.jackoder.progdispatcher;

import com.jackoder.progdispatcher.base.BasicTestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jackoder
 * @version 2015/12/30.
 */
public class ListenerTestCase extends BasicTestCase {

    private OnProgressListener mOnProgressListener = new OnProgressListener() {
        @Override
        public void onProgress(String id, int progress, Object context) {

        }

        @Override
        public void onError(String id, Throwable throwable) {

        }
    };

    @Test
    public void testListenerAddRemove() {
        Assert.assertFalse(ProgressDispatcher.getInstance().removeOnProgressListener(null, mOnProgressListener));
        ProgressDispatcher.getInstance().addOnProgressListener(null, mOnProgressListener);
        Assert.assertFalse(ProgressDispatcher.getInstance().removeOnProgressListener("false", mOnProgressListener));
        Assert.assertTrue(ProgressDispatcher.getInstance().removeOnProgressListener(null, mOnProgressListener));
        Assert.assertFalse(ProgressDispatcher.getInstance().removeOnProgressListener(null, mOnProgressListener));

        ProgressDispatcher.getInstance().addOnProgressListener(null, mOnProgressListener);
        ProgressDispatcher.getInstance().addOnProgressListener(null, mOnProgressListener);
        Assert.assertTrue(ProgressDispatcher.getInstance().removeOnProgressListener(null, mOnProgressListener));
        Assert.assertFalse(ProgressDispatcher.getInstance().removeOnProgressListener(null, mOnProgressListener));
    }

    @Test
    public void testListenerCall() {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        ProgressDispatcher.getInstance().addOnProgressListener("Target", new OnProgressListener() {
            @Override
            public void onProgress(String id, int progress, Object context) {
                countDownLatch.countDown();
            }

            @Override
            public void onError(String id, Throwable throwable) {
                countDownLatch.countDown();
            }
        });
        ProgressDispatcher.getInstance().getProgressObserver("Target").onNext(new Progress());
        ProgressDispatcher.getInstance().getProgressObserver("Target").onError(new Exception());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testRelease() {
        ProgressDispatcher.getInstance().addOnProgressListener(null, mOnProgressListener);
        ProgressDispatcher.getInstance().release();
        Assert.assertFalse(ProgressDispatcher.getInstance().removeOnProgressListener(null, mOnProgressListener));
    }

    @Test
    public void testListenerId() {
        final AtomicInteger count = new AtomicInteger();
        ProgressDispatcher.getInstance().addOnProgressListener(null, new OnProgressListener() {
            @Override
            public void onProgress(String id, int progress, Object context) {
                count.incrementAndGet();
            }

            @Override
            public void onError(String id, Throwable throwable) {

            }
        });
        ProgressDispatcher.getInstance().addOnProgressListener("Test", new OnProgressListener() {
            @Override
            public void onProgress(String id, int progress, Object context) {
                count.incrementAndGet();
            }

            @Override
            public void onError(String id, Throwable throwable) {

            }
        });
        Progress progress = new Progress();
        ProgressDispatcher.getInstance().getProgressObserver(null).onNext(progress);
        ProgressDispatcher.getInstance().getProgressObserver("Test").onNext(progress);
        Assert.assertEquals(3, count.get());
    }

}
