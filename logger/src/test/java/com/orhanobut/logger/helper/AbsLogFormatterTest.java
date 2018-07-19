package com.orhanobut.logger.helper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Kale
 * @date 2018/7/19
 */
public class AbsLogFormatterTest {

    private AbsLogFormatter formatter;

    @Before
    public void setUp() {
        formatter = new AbsLogFormatter() {
            @Override
            protected String getMsgHeader() {
                return null;
            }

            @Override
            protected String getMsgFooter() {
                return null;
            }

            @Override
            protected String getFormatMsgLine(String message, int line, int lineCount) {
                return null;
            }
        };
    }

    @Test
    public void test_set_and_getDelegate() {
        formatter.setDelegate(null);

        assertNull(formatter.getDelegate());

        LogDelegate delegate = new LogDelegate(LogSettings.create(), formatter, new ILogPrinter() {
            @Override
            public void println(int priority, String tag, String message, Throwable throwable) {

            }
        });

        formatter.setDelegate(delegate);
        assertEquals("delegate is not same", delegate, formatter.getDelegate());
    }

    @Test(expected = NullPointerException.class)
    public void test_set_null_getDelegate() {
        formatter.setDelegate(null);
        formatter.hasCustomTag();
    }

    @Test
    public void test_hasCustomTag_return_true() {
        LogDelegate delegate = new LogDelegate(LogSettings.create(), formatter, new ILogPrinter() {
            @Override
            public void println(int priority, String tag, String message, Throwable throwable) {

            }
        });

        formatter.setDelegate(delegate);
        assertTrue("error!!!!!", formatter.hasCustomTag());
    }

}