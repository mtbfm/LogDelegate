package com.orhanobut.logger.helper;

import android.support.annotation.Nullable;

/**
 * @author Kale
 * @date 2017/9/28
 */
public class DefaultFormatter extends AbsLogFormatter {

    private StringBuilder sb = new StringBuilder();

    private final int tailOffset;

    public DefaultFormatter() {
        tailOffset = 0;
    }

    public DefaultFormatter(int tailOffset) {
        this.tailOffset = tailOffset;
    }

    @Nullable
    @Override
    protected String getMsgHeader() {
        return null;
    }

    @Nullable
    @Override
    protected String getMsgFooter() {
        return null;
    }

    @Nullable
    @Override
    public String getFormatMsgLine(String message, int line, int lineCount) {
        if (line == lineCount - 1) {
            // last line or single line
            String s = "";
            if (getSettings().isShowThreadInfo()) {
                s = " Thread: " + Thread.currentThread().getName(); // Thread:main
            }
            return "└ " + message + getTail() + s;
        } else {
            return "│ " + message;
        }
    }

    /**
     * @return ==> onCreate(MainActivity.java:827) Thread:main
     */
    private String getTail() {
        if (!getSettings().isShowMethodLink()) {
            return "";
        }

        sb.setLength(0);

        int index = LogPrintDelegate.BASE_STACK_OFFSET + getSettings().getMethodOffset() + tailOffset;
        if (hasCustomTag()) {
            // 如果自定义的tag，那么会多走一个方法
            index -= 1;
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        final StackTraceElement stack = index >= stackTrace.length ? stackTrace[stackTrace.length - 1] : stackTrace[index];

        if (sb.length() < 0) {
            sb = new StringBuilder();
        } else {
            sb.setLength(0);
        }

        sb.append(String.format(" ┄┄> %s(%s:%s)",
                stack.getMethodName(),
                stack.getFileName(),
                stack.getLineNumber()));

        return sb.toString();
    }

}
