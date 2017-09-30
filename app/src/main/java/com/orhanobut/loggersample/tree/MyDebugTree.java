package com.orhanobut.loggersample.tree;

import android.support.annotation.NonNull;
import android.util.Log;

import com.orhanobut.logger.helper.LogPrintDelegate;
import com.orhanobut.logger.helper.LogSettings;
import com.orhanobut.loggersample.format.PrettyFormatter;

import timber.log.Timber;

/**
 * extends {@link timber.log.Timber.Tree} for make log pretty
 */
public final class MyDebugTree extends Timber.DebugTree {

    private LogPrintDelegate mDelegate;

    public MyDebugTree(LogSettings settings) {
        mDelegate = new LogPrintDelegate(settings, new PrettyFormatter(),
                (priority, tag, message, throwable) -> Log.println(priority, tag, message));
    }

    @Override
    protected boolean isLoggable(String tag, int priority) {
        return mDelegate.isLoggable(priority, tag);
    }

    @Override
    protected String createStackElementTag(StackTraceElement element) {
        return mDelegate.getAutoTag(element);
    }

    @Override
    protected void log(int priority, String tag, @NonNull String message, Throwable t) {
        mDelegate.printLog(priority, tag, message, t);
    }

}
