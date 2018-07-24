package com.orhanobut.logger.helper;

import com.orhanobut.logger.helper.formatter.DefaultLogFormatter;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kale
 * @date 2018/7/24
 */
public class LogDelegateTest {

    @Test
    public void testGetAutoTag_return_TestClass() {
        LogSettings settings = new LogSettings.Builder()
                .methodOffset(-LogDelegate.MIN_STACK_OFFSET)
                .build();

        LogDelegate delegate = new LogDelegate(settings, new DefaultLogFormatter(), null);

        String tag = delegate.getAutoTag(null); // 直接调用此方法，所以方法的offset要特殊设置，LogDelegate.MIN_STACK_OFFSET

        Assert.assertEquals(LogDelegateTest.class.getSimpleName(), tag);
    }

    @Test
    public void testGetAutoTag_return_MainActivity() {
        LogDelegate delegate = new LogDelegate(LogSettings.create(), new DefaultLogFormatter(), null);

        StackTraceElement element = new StackTraceElement("com.abc.kale.MainActivity", "test()", "ddd", 31);

        String tag = delegate.getAutoTag(element);

        Assert.assertEquals("MainActivity", tag);
    }

    @Test
    public void testGetAutoTag_return_globalTag() {
        LogSettings settings = new LogSettings.Builder()
                .globalTag("GLOBAL_TAG")
                .build();

        LogDelegate delegate = new LogDelegate(settings, new DefaultLogFormatter(), null);

        String tag = delegate.getAutoTag(null); // 直接调用此方法，所以方法的offset要特殊设置，LogDelegate.MIN_STACK_OFFSET

        Assert.assertEquals("GLOBAL_TAG", tag);
    }

    @Test
    public void testGetAutoTag_setEmptyTag_return_globalTag() {
        LogSettings settings = new LogSettings.Builder()
                .globalTag("") // empty global tag
                .build();

        LogDelegate delegate = new LogDelegate(settings, new DefaultLogFormatter(), null);

        StackTraceElement element = new StackTraceElement("com.abc.kale.MainActivity", "test()", "ddd", 31);
        String tag = delegate.getAutoTag(element); 

        Assert.assertEquals("MainActivity", tag);
    }


    @Test
    public void testGetAutoTag_seTagPrefix() {
        LogSettings settings = new LogSettings.Builder()
                .tagPrefix("kale")
                .build();

        LogDelegate delegate = new LogDelegate(settings, new DefaultLogFormatter(), null);

        StackTraceElement element = new StackTraceElement("com.abc.kale.MainActivity", "test()", "ddd", 31);
        String tag = delegate.getAutoTag(element);

        Assert.assertEquals("kale-MainActivity", tag);
    }

}