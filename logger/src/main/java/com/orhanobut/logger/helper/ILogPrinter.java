package com.orhanobut.logger.helper;

/**
 * @author Kale
 * @date 2018/7/19
 */
public interface ILogPrinter {

    void println(int priority, String tag, String message, Throwable throwable);

}