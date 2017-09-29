package com.orhanobut.logger.helper;

import android.support.annotation.Nullable;

/**
 * @author Kale
 * @date 2017/9/28
 */
public class DefaultStyle extends AbsLogStyle {

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
    public String getMsgLine(String message, int line, int lineCount) {
        if (line == lineCount - 1) {
            // last line or single line
            return "└ " + message + getTail();
        } else {
            return "│ " + message;
        }
    }

    /**
     * @return ==> onCreate(MainActivity.java:827) Thread:main
     */
    private String getTail() {
        StringBuilder sb = new StringBuilder();
        if (!getSettings().isShowMethodLink()) {
            return "";
        }

        int index = LogPrintHelper.BASE_STACK_OFFSET + getSettings().getMethodOffset() + 1;
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

        if (getSettings().isShowThreadInfo()) {
            sb.append(" Thread: ").append(Thread.currentThread().getName()); // Thread:main
        }
        return sb.toString();
    }


}
