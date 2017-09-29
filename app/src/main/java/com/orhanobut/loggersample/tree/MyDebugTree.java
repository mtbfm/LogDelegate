package com.orhanobut.loggersample.tree;

import android.support.annotation.NonNull;
import android.util.Log;

import com.orhanobut.logger.helper.DefaultFormat;
import com.orhanobut.logger.helper.LogPrintDelegate;
import com.orhanobut.logger.helper.LogSettings;

import timber.log.Timber;

/**
 * extends {@link timber.log.Timber.Tree} for make log pretty
 */
public final class MyDebugTree extends Timber.DebugTree implements LogPrintDelegate.ILog {

    private LogPrintDelegate mDelegate;

    public MyDebugTree(LogSettings settings) {
        mDelegate = new LogPrintDelegate(settings, new DefaultFormat(), this);
    }

    @Override
    protected String createStackElementTag(StackTraceElement element) {
        return mDelegate.getAutoTag(element);
    }

    @Override
    protected void log(int priority, String tag, @NonNull String message, Throwable t) {
        mDelegate.printLog(priority, tag, message, t);
    }

    @Override
    public void println(int priority, String tag, String message, Throwable throwable) {
        Log.println(priority, tag, message);
    }

}
