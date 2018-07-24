package com.orhanobut.loggersample;

import android.util.Log;

import com.orhanobut.logger.helper.LogDelegate;
import com.orhanobut.logger.helper.LogSettings;
import com.orhanobut.logger.helper.formatter.DefaultLogFormatter;

/**
 * @author Kale
 * @date 2017/9/28
 */
public final class LogUtils {

    private static LogDelegate sDelegate;

    public static void init(LogSettings settings) {
        sDelegate = new LogDelegate(settings, new DefaultLogFormatter(),
                (priority, tag, message, throwable) -> Log.println(priority, tag, message));
    }

    public static boolean isLoggable(int priority, String tag) {
        return sDelegate.isLoggable(priority, tag);
    }

    public static void d(String tag, String message) {
        if (isLoggable(Log.DEBUG, tag)) {
            print(Log.DEBUG, tag, message);
        }
    }

    public static void d(String message) {
        String tag = sDelegate.getAutoTag(null);
        d(tag, message);
    }

    public static void e(String tag, String message) {
        if (isLoggable(Log.ERROR, tag)) {
            print(Log.ERROR, tag, message);
        }
    }

    public static void openLog(boolean b) {
        if (b) {
            sDelegate.getSettings().changeLogLev(Log.VERBOSE);
        } else {
            sDelegate.getSettings().closeLog();
        }
    }

    private static void print(int priority, String tag, String message) {
        sDelegate.printLog(priority, tag, message, null);
    }

}
