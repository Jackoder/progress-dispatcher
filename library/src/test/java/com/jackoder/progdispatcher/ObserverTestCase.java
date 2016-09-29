package com.jackoder.progdispatcher;

import com.jackoder.progdispatcher.base.BasicTestCase;

import org.junit.Assert;
import org.junit.Test;

import rx.Observer;

/**
 * @author Jackoder
 * @version 2016/9/29
 */
public class ObserverTestCase extends BasicTestCase {

    @Test
    public void testEmptyObserver() {
        Assert.assertNotNull(ProgressDispatcher.getInstance().getProgressObserver(null));
    }

    @Test
    public void testUnSubscribe() {
        Observer<Progress> observer = ProgressDispatcher.getInstance().getProgressObserver("Test");
        ProgressDispatcher.getInstance().release();
        Assert.assertNotEquals(observer, ProgressDispatcher.getInstance().getProgressObserver("Test"));
    }
}
