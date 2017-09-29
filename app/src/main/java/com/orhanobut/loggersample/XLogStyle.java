package com.orhanobut.loggersample;

import android.support.annotation.Nullable;

import com.orhanobut.logger.helper.AbsLogStyle;
import com.orhanobut.logger.helper.LogPrintHelper;

/**
 * @author Kale
 * @date 2016/12/8
 */

public class XLogStyle extends AbsLogStyle {

    @Nullable
    @Override
    protected String getMsgHeader() {
        String top = "";
        top += "╔════════════════════════════════════════════════════════════════════════════";
        top += "\n║Thread:" + Thread.currentThread().getName();
        top += "\n║────────────────────────────────────────────────────────────────────────────";
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        String format = format(stack);
        top += "\n" + format;
        top += "║────────────────────────────────────────────────────────────────────────────";
        return top;
    }

    private String format(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder(256);
        if (stackTrace == null || stackTrace.length == 0) {
            return null;
        } else if (stackTrace.length == 1) {
            return "\t─ " + stackTrace[0].toString();
        } else {
            int index = LogPrintHelper.BASE_STACK_OFFSET + getSettings().getMethodOffset();
            for (int i = index, N = stackTrace.length; i < N - index; i++) {
                if (i != N - index - 1) {
                    sb.append("║\t├ ");
                    sb.append(stackTrace[i].toString());
                } else {
                    sb.append("║\t└ ");
                    sb.append(stackTrace[i].toString());
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    @Nullable
    @Override
    protected String getMsgFooter() {
        return "╚════════════════════════════════════════════════════════════════════════════";
    }

    @Nullable
    @Override
    protected String getMsgLine(String message, int line, int lineCount) {
        String prefix = "║";
        if (lineCount == 1) {
            return prefix + message;
        }

        if (line == lineCount - 1) {
            // last
            prefix += message;
        } else {
            prefix += message;
        }
        return prefix;
    }
}
