package com.orhanobut.loggersample;

import android.util.Log;

import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.helper.LogPrintDelegate;
import com.orhanobut.logger.helper.LogSettings;
import com.orhanobut.loggersample.format.PrettyFormat;

/**
 * @author Kale
 * @date 2017/9/28
 */
public class MyLogAdapter implements LogAdapter, LogPrintDelegate.ILog {

    private LogPrintDelegate mDelegate;

    public MyLogAdapter(LogSettings settings) {
        mDelegate = new LogPrintDelegate(settings, new PrettyFormat(), this);
    }

    @Override
    public boolean isLoggable(int priority, String tag) {
        return mDelegate.isLoggable(priority, tag);
    }

    @Override
    public void log(int priority, String tag, String message) {
        tag = mDelegate.getAutoTag(null);
        mDelegate.printLog(priority, tag, message, null);
    }

    @Override
    public void println(int priority, String tag, String message, Throwable throwable) {
        Log.println(priority, tag, message);
    }
}
