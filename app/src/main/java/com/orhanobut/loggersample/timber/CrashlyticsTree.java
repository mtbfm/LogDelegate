package com.orhanobut.loggersample.timber;

import android.support.annotation.Nullable;
import android.util.Log;

import timber.log.Timber;

/**
 * @author Kale
 * @date 2016/5/23
 */
public class CrashlyticsTree extends Timber.Tree {

    @Override
    protected boolean isLoggable(String tag, int priority) {
        return super.isLoggable(tag, priority);
    }

    @Override
    protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return;
        }

        if (t == null && message != null) {
//            Crashlytics.logException(new Exception(message));
        } else if (t != null && message != null) {
//            Crashlytics.logException(new Exception(message, t));
        } else if (t != null) {
//            Crashlytics.logException(t);
        }
    }
}
