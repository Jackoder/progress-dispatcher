package com.jackoder.progdispatcher;

import com.jackoder.progdispatcher.base.BaseTestCase;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jackoder
 * @version 2016/9/29
 */
public class InitTestCase extends BaseTestCase {

    @Test
    public void testMultiInit() {
        ProgressDispatcher progressDispatcher = ProgressDispatcher.getInstance();
        ProgressDispatcher.init(null);
        Assert.assertTrue(progressDispatcher == ProgressDispatcher.getInstance());
    }

    @Test
    public void testRelease() {
        ProgressDispatcher progressDispatcher = ProgressDispatcher.getInstance();
        ProgressDispatcher.getInstance().release();
        Assert.assertFalse(progressDispatcher == ProgressDispatcher.getInstance());
    }

}
