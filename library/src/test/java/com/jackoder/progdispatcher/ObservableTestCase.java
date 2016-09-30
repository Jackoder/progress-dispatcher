package com.jackoder.progdispatcher;

import com.jackoder.progdispatcher.base.BasicTestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * @author Jackoder
 * @version 2016/9/29
 */
public class ObservableTestCase extends BasicTestCase {

    final String  id       = "Test";
    final int     progress = 77;
    final boolean context  = true;
    final String  error    = "Custom Error";

    Progress       mProgress;
    Throwable      mThrowable;

    @Test
    public void testEmptyObservable() {
        Assert.assertNotNull(ProgressDispatcher.getInstance().getProgressObservable(null));
    }

    @Test
    public void testObservable() {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        ProgressDispatcher.getInstance().getProgressObservable(null)
                .subscribe(new Action1<Progress>() {
                    @Override
                    public void call(Progress progress) {
                        mProgress = progress;
                        countDownLatch.countDown();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mThrowable = throwable;
                        countDownLatch.countDown();
                    }
                });
        Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                Progress prog = new Progress(id);
                prog.setProgress(progress);
                prog.setContext(context);
                ProgressDispatcher.getInstance().getProgressObserver(null).onNext(prog);
                ProgressDispatcher.getInstance().getProgressObserver(null).onError(new Exception(error));
                return Observable.just(null);
            }
        }).subscribeOn(Schedulers.newThread()).subscribe();
        try {
            countDownLatch.await();
            Assert.assertEquals(id, mProgress.getId());
            Assert.assertEquals(progress, mProgress.getProgress());
            Assert.assertEquals(context, mProgress.getContext());

            countDownLatch.await();
            Assert.assertTrue(mThrowable.getMessage().equals(error));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testObservable2() {
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        ProgressDispatcher.getInstance().getProgressObservable(null)
                .subscribe(new Action1<Progress>() {
                    @Override
                    public void call(Progress progress) {
                        mProgress = progress;
                        countDownLatch.countDown();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mThrowable = throwable;
                        countDownLatch.countDown();
                    }
                });
        Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                Progress prog = new Progress(id);
                prog.setProgress(progress);
                prog.setContext(context);
                Observer<Progress> observer = ProgressDispatcher.getInstance().getProgressObserver(null);
                ProgressDispatcher.getInstance().release();
                observer.onNext(prog);
                observer.onError(new Exception(error));
                return Observable.just(null);
            }
        }).subscribeOn(Schedulers.newThread()).subscribe();
        try {
            Assert.assertNotEquals(0, countDownLatch.await(2, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
