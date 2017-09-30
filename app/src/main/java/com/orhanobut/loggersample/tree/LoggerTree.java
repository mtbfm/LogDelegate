package com.orhanobut.loggersample.tree;

import android.support.annotation.Nullable;
import android.util.Log;

import timber.log.Timber;

/**
 * @author Kale
 * @date 2016/5/23
 */
public class LoggerTree extends Timber.DebugTree{

    @Override
    protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable t) {
        Log.println(priority, tag, message);
    }
}