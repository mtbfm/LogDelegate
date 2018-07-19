package com.orhanobut.loggersample.tree;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;


import timber.log.Timber;

/**
 * @author Kale
 * @date 2016/5/23
 */
public class CrashlyticsTree extends Timber.DebugTree {

    public CrashlyticsTree(Context context) {
        logUserInfo();
    }

    private void logUserInfo() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
//        Crashlytics.setUserIdentifier("12345");
//        Crashlytics.setUserEmail("user@fabric.io");
//        Crashlytics.setUserName("Test User");
    }

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
