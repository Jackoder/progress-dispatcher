package com.jackoder.progdispatcher;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jackoder
 * @version 2016/9/23
 */
public class Progress implements Serializable {

    private static AtomicInteger sIdGenerator = new AtomicInteger();

    String mId;
    int    mProgress;
    Object mContext;

    public Progress() {
        mId = generateId();
    }

    public Progress(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    protected void setId(String id) {
        mId = id;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int progress) {
        mProgress = progress;
    }

    public Object getContext() {
        return mContext;
    }

    public void setContext(Object context) {
        mContext = context;
    }

    private String generateId() {
        return "ID_" + sIdGenerator.incrementAndGet();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Progress)) {
            return false;
        } else if (o == this) {
            return true;
        } else if (getId().equals(((Progress) o).getId())
                && getProgress() == ((Progress) o).getProgress()
                && getContext().equals(((Progress) o).getContext())) {
            return true;
        }
        return false;
    }
}
