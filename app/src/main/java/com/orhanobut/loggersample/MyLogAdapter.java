package com.orhanobut.loggersample;

import android.util.Log;

import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.helper.DefaultStyle;
import com.orhanobut.logger.helper.LogPrintHelper;
import com.orhanobut.logger.helper.LogSettings;

/**
 * @author Kale
 * @date 2017/9/28
 */
public class MyLogAdapter implements LogAdapter, LogPrintHelper.ILog {

    private LogPrintHelper helper;

    public MyLogAdapter(LogSettings settings) {
        helper = new LogPrintHelper(settings, new DefaultStyle(), this);
    }

    @Override
    public boolean isLoggable(int priority, String tag) {
        return helper.isLoggable(priority, tag);
    }

    @Override
    public void log(int priority, String tag, String message) {
        tag = helper.getAutoTag(null);
        helper.printLog(priority, tag, message, null);
    }

    @Override
    public void println(int priority, String tag, String message, Throwable throwable) {
        Log.println(priority, tag, message);
    }
}
