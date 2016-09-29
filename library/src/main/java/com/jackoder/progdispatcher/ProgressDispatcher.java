package com.jackoder.progdispatcher;

import android.os.Handler;
import android.util.Log;

import com.trello.rxlifecycle.RxLifecycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * @author Jackoder
 * @version 2016/9/23
 */
public class ProgressDispatcher {

    private static final String TAG = "ProgressDispatcher";

    private static ProgressDispatcher sInstance = null;

    private enum State {
        START,
        STOP
    }

    private final Map<String, PublishSubject<Progress>> mPublishSubjectMap    = new HashMap<>();
    private final Map<String, Set<OnProgressListener>>  mOnProgressListeners  = new HashMap<>();
    private final BehaviorSubject<State>                mStateBehaviorSubject = BehaviorSubject.create(State.START);
    private final Scheduler mObserveScheduler;

    private ProgressDispatcher(Handler listenerHandler) {
        if (listenerHandler == null) {
            mObserveScheduler = AndroidSchedulers.mainThread();
        } else {
            mObserveScheduler = HandlerScheduler.from(listenerHandler);
        }
    }

    public synchronized static void init(Handler listenerHandler) {
        if (sInstance == null) {
            sInstance = new ProgressDispatcher(listenerHandler);
            Log.i(TAG, "create new");
            return;
        }
        Log.i(TAG, "already created");
    }

    public static ProgressDispatcher getInstance() {
        if (sInstance == null) {
            init(null);
        }
        return sInstance;
    }

    public void release() {
        mStateBehaviorSubject.onNext(State.STOP);
        synchronized (mOnProgressListeners) {
            mOnProgressListeners.clear();
        }
        mPublishSubjectMap.clear();
        sInstance = null;
    }

    public void addOnProgressListener(String id, OnProgressListener onProgressListener) {
        if (mOnProgressListeners.containsKey(id) && mOnProgressListeners.get(id).contains(onProgressListener)) {
            return;
        } else {
            if (!mOnProgressListeners.containsKey(id)) {
                mOnProgressListeners.put(id, new HashSet<OnProgressListener>());
            }
            synchronized (mOnProgressListeners) {
                mOnProgressListeners.get(id).add(onProgressListener);
            }
            createSubjectIfNotFound(id);
        }
    }

    public boolean removeOnProgressListener(String id, OnProgressListener onProgressListener) {
        synchronized (mOnProgressListeners) {
            if (mOnProgressListeners.containsKey(id) && mOnProgressListeners.get(id).contains(onProgressListener)) {
                return mOnProgressListeners.get(id).remove(onProgressListener);
            }
        }
        return false;
    }

    public Observable<Progress> getProgressObservable(String id) {
        createSubjectIfNotFound(id);
        return mPublishSubjectMap.get(id);
    }

    public Observer<Progress> getProgressObserver(String id) {
        createSubjectIfNotFound(id);
        return mPublishSubjectMap.get(id);
    }

    private void createSubjectIfNotFound(final String id) {
        if (!mPublishSubjectMap.containsKey(id)) {
            mPublishSubjectMap.put(id, createPublishSubject(id));
        }
    }

    private PublishSubject<Progress> createPublishSubject(final String id) {
        final PublishSubject<Progress> subject = PublishSubject.create();
        Observable.Transformer<Progress, Progress> transformer = RxLifecycle.bindUntilEvent(mStateBehaviorSubject, State.STOP);
        subject.compose(transformer).observeOn(mObserveScheduler);
        //FIXME 没有调到doOnUnsubscribe
        subject/*.doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        mPublishSubjectMap.remove(id);
                        Log.d(TAG, "Subject(" + id + ") unsubscribe.");
                    }
                })*/.subscribe(new Action1<Progress>() {
                    @Override
                    public void call(Progress progress) {
                        Log.d(TAG, "Subject(" + id + ") progress.");
                        List<OnProgressListener> progressListeners =
                                new ArrayList<OnProgressListener>(getOnProgressListenerById(id));
                        for (OnProgressListener onProgressListener : progressListeners) {
                            onProgressListener.onProgress(id, progress.getProgress(), progress.getContext());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG, "Subject(" + id + ") error.");
                        List<OnProgressListener> progressListeners =
                                new ArrayList<OnProgressListener>(getOnProgressListenerById(id));
                        for (OnProgressListener onProgressListener : progressListeners) {
                            onProgressListener.onError(id, throwable);
                        }
                    }
                });
        return subject;
    }

    private Set<OnProgressListener> getOnProgressListenerById(String id) {
        Set<OnProgressListener> progressListenerSet = new HashSet<>();
        if (mOnProgressListeners.containsKey(id)) {
            progressListenerSet.addAll(mOnProgressListeners.get(id));
        }
        if (mOnProgressListeners.containsKey(null)) {
            progressListenerSet.addAll(mOnProgressListeners.get(null));
        }
        return progressListenerSet;
    }

}
