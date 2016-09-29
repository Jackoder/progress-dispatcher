package com.jackoder.progdispatcher;

import com.jackoder.progdispatcher.base.BaseTestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jackoder
 * @version 2016/9/29
 */
public class ProgressTestCase extends BaseTestCase {

    @Test
    public void testProgress() {
        Progress progress = new Progress();

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(progress.getId());
        matcher.find();
        Assert.assertNotNull(matcher.group());

        int startIndex = Integer.valueOf(matcher.group());
        progress = new Progress();
        Assert.assertEquals(progress.getId(), "ID_" + (++startIndex));
        progress = new Progress();
        Assert.assertEquals(progress.getId(), "ID_" + (++startIndex));
        progress = new Progress("Test");
        Assert.assertEquals(progress.getId(), "Test");
    }

    @Test
    public void testEqualProgress() {
        Progress progress1 = new Progress("Test");
        progress1.setProgress(1);
        progress1.setContext(true);

        Progress progress2 = new Progress("Test");
        progress2.setProgress(1);
        progress2.setContext(true);

        Progress progress3 = new Progress();
        progress3.setProgress(1);
        progress3.setContext(true);

        Progress progress4 = new Progress("Test");
        progress4.setProgress(2);
        progress4.setContext(true);

        Progress progress5 = new Progress("Test");
        progress5.setProgress(1);
        progress5.setContext(false);

        Assert.assertFalse(progress1.equals(null));
        Assert.assertTrue(progress1.equals(progress1));
        Assert.assertTrue(progress1.equals(progress2));
        Assert.assertFalse(progress1.equals(progress3));
        Assert.assertFalse(progress1.equals(progress4));
        Assert.assertFalse(progress1.equals(progress5));
    }

    @Test
    public void testSetId() {
        Progress progress = new Progress();
        String temp = progress.getId();
        progress.setId("Test");
        Assert.assertTrue(!temp.equals(progress.getId()) && "Test".equals(progress.getId()));
    }
}
