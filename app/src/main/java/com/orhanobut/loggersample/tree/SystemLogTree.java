package com.orhanobut.loggersample.tree;

import android.support.annotation.Nullable;
import android.util.Log;

import timber.log.Timber;

/**
 * @author Kale
 * @date 2016/5/23
 */
public class SystemLogTree extends Timber.DebugTree {

    @Override
    protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable t) {
        if (priority < Log.WARN) {
            System.out.println(message);
        } else {
            System.err.println(message);
        }
    }
}