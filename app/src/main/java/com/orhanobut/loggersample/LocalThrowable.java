package com.orhanobut.loggersample;

/**
 * @author Kale
 * @date 2017/9/30
 */
public class LocalThrowable extends Throwable{

    public LocalThrowable() {
    }

    public LocalThrowable(String message) {
        super(message);
    }

    public LocalThrowable(String message, Throwable cause) {
        super(message, cause);
    }

    public LocalThrowable(Throwable cause) {
        super(cause);
    }

    public LocalThrowable(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
