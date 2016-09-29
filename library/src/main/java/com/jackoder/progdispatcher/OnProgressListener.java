package com.jackoder.progdispatcher;

/**
 * @author Jackoder
 * @version 2016/9/23
 */
public interface OnProgressListener {

    void onProgress(String id, int progress, Object context);

    void onError(String id, Throwable throwable);
}
