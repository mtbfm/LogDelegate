package com.orhanobut.logger.helper;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Kale
 * @date 2018/7/19
 */
public class LogSettingsTest {

    @Test
    public void test_defaultSettings_params() {
        LogSettings settings = LogSettings.create();

        assertEquals(0, settings.methodOffset);
        assertTrue(settings.showMethodLink);
        assertFalse(settings.showThreadInfo);
        assertNull(settings.tagPrefix);
        assertNull(settings.globalTag);
        assertEquals(Log.VERBOSE, settings.priority);
    }

    @Test
    public void test_settingsBuilder() {
        LogSettings settings = new LogSettings.Builder()
                .globalTag("kale")
                .logPriority(Log.DEBUG)
                .methodOffset(21)
                .showMethodLink(true)
                .showThreadInfo(true)
                .tagPrefix("MainActivity-").build();

        assertEquals(21, settings.methodOffset);
        assertTrue(settings.showMethodLink);
        assertTrue(settings.showThreadInfo);
        assertEquals("MainActivity-", settings.tagPrefix);
        assertEquals("kale", settings.globalTag);
        assertEquals(Log.DEBUG, settings.priority);
    }
    
    @Test
    public void test_closeLog(){
        LogSettings settings = LogSettings.create();
        
        assertEquals(Log.VERBOSE, settings.priority);

        settings.closeLog();

        assertEquals(Log.ASSERT, settings.priority);
    }

    @Test
    public void test_changeLogLev_return_Warn(){
        LogSettings settings = new LogSettings.Builder().logPriority(Log.ASSERT).build();

        settings.changeLogLev(Log.WARN);

        assertEquals(Log.WARN, settings.priority);
    }

}