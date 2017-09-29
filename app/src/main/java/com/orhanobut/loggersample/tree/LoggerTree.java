package com.orhanobut.loggersample.tree;

import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

import timber.log.Timber;

/**
 * @author Kale
 * @date 2016/5/23
 */
public class LoggerTree extends Timber.Tree {

    @Override
    protected boolean isLoggable(String tag, int priority) {
        return true;
    }

    @Override
    protected void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable t) {
        Logger.log(priority, tag, message, t);
    }
}