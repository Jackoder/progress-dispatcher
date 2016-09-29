package com.jackoder.progdispatcher.base;

import com.jackoder.progdispatcher.ProgressDispatcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

/**
 * @author Jackoder
 * @version 2016/9/29
 */
@Ignore("基类不处理")
public class BasicTestCase extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        configLog();
        ProgressDispatcher.init(null);
    }

    @After
    public void tearDown() throws Exception {
        ProgressDispatcher.getInstance().release();
    }
}
