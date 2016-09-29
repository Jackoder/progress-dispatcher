package com.jackoder.sample.app.utils;

/**
 * @author Jackoder
 * @version 2016/9/29
 */
public class LogFormater {

    public static String format(String id, int progress, Object context) {
        return new StringBuilder("Id: ").append(id).append(" Progress: ").append(progress).append(" Context: ").append(context).toString();
    }

    public static String format(String id, Throwable throwable) {
        return new StringBuilder("Id: ").append(id).append(" Throwable: ").append(throwable.getMessage()).toString();
    }
}
