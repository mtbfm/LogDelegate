package com.orhanobut.logger.helper;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Kale
 * @date 2018/7/19
 */
public class ILogPrinterTest {

    @Test
    public void println() {
        ILogPrinter printer = new ILogPrinter() {
            @Override
            public void println(int priority, String tag, String message, Throwable throwable) {
                assertEquals(Log.ERROR, priority);
                assertEquals("tag", tag);
                assertEquals("message", message);
                assertNull(throwable);
            }
        };

        printer.println(Log.ERROR, "tag", "message", null);
    }
}