package com.jackoder.progdispatcher.base;

import com.jackoder.progdispatcher.BuildConfig;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * @author Jackoder
 * @version 2016/09/23.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = IConfig.EMULATE_SDK, manifest = IConfig.MANIFEST)
@Ignore("基类不处理")
public class BaseTestCase {

    protected void configLog() {
        ShadowLog.stream = System.out;
    }

}
