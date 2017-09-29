package com.orhanobut.loggersample.timber;

import android.support.annotation.NonNull;
import android.util.Log;

import com.orhanobut.logger.helper.DefaultStyle;
import com.orhanobut.logger.helper.LogPrintHelper;
import com.orhanobut.logger.helper.LogSettings;

import timber.log.Timber;

/**
 * extends {@link timber.log.Timber.Tree} for make log pretty
 */
public final class TimberDebugTree extends Timber.DebugTree implements LogPrintHelper.ILog {

    private LogPrintHelper helper;

    TimberDebugTree(LogSettings settings) {
        helper = new LogPrintHelper(settings, new DefaultStyle(), this);
    }

    @Override
    protected String createStackElementTag(StackTraceElement element) {
        return helper.getAutoTag(null);
    }

    @Override
    protected void log(int priority, String tag, @NonNull String message, Throwable t) {
        helper.printLog(priority, tag, message, t);
    }

    @Override
    public void println(int priority, String tag, String message, Throwable throwable) {
        Log.println(priority, tag, message);
    }

}
