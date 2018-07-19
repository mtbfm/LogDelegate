package com.orhanobut.loggersample.tree;

import android.support.annotation.NonNull;
import android.util.Log;

import com.orhanobut.logger.helper.AbsLogFormatter;
import com.orhanobut.logger.helper.LogDelegate;
import com.orhanobut.logger.helper.LogSettings;
import com.orhanobut.logger.helper.formatter.DefaultLogFormatter;

import timber.log.Timber;

/**
 * extends {@link Timber.Tree} to print log
 */
public class DefaultLogTree extends Timber.DebugTree {

    private LogDelegate mDelegate;

    public DefaultLogTree(LogSettings settings) {
        this(settings, new DefaultLogFormatter());
    }

    DefaultLogTree(LogSettings settings, AbsLogFormatter formatter) {
        mDelegate = new LogDelegate(settings, formatter,
                (priority, tag, message, throwable) -> Log.println(priority, tag, message));
    }

    @Override
    protected boolean isLoggable(String tag, int priority) {
        return mDelegate.isLoggable(priority, tag);
    }

    @Override
    protected String createStackElementTag(@NonNull StackTraceElement element) {
        return mDelegate.getAutoTag(element);
    }

    @Override
    protected void log(int priority, String tag, @NonNull String message, Throwable t) {
        mDelegate.printLog(priority, tag, message, t);
    }

}
