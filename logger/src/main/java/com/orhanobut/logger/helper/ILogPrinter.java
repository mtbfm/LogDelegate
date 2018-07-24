package com.orhanobut.logger.helper;

import android.support.annotation.Nullable;

/**
 * @author Kale
 * @date 2018/7/19
 */
public interface ILogPrinter {

    void println(int priority, String tag, String message, @Nullable Throwable throwable);

}