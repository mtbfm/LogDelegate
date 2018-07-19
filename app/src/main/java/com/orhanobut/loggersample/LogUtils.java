package com.orhanobut.loggersample;

import android.util.Log;

import com.orhanobut.logger.helper.LogDelegate;
import com.orhanobut.logger.helper.ILogPrinter;
import com.orhanobut.logger.helper.LogSettings;
import com.orhanobut.loggersample.format.PrettyFormatter;

/**
 * @author Kale
 * @date 2017/9/28
 */
public class LogUtils implements ILogPrinter {

    private LogDelegate mDelegate;

    public LogUtils(LogSettings settings) {
        mDelegate = new LogDelegate(settings, new PrettyFormatter(), this);
    }

    public boolean isLoggable(int priority, String tag) {
        return mDelegate.isLoggable(priority, tag);
    }

    public void log(int priority, String tag, String message) {
        tag = mDelegate.getAutoTag(null);
        mDelegate.printLog(priority, tag, message, null);
    }

    @Override
    public void println(int priority, String tag, String message, Throwable throwable) {
        Log.println(priority, tag, message);
    }
}
