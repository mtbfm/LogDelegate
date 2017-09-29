package com.orhanobut.logger.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * @author Kale
 * @date 2017/9/28
 */
public class LogPrintHelper {

    public static final int BASE_STACK_OFFSET = 10;

    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");

    private static final String PROPERTY = System.getProperty("line.separator");

    private static final int MIN_STACK_OFFSET = 5;

    private static final int MAX_LOG_LENGTH = 4000;

    private static final int MAX_TAG_LENGTH = 23;

    private StringBuilder sb;

    private final LogSettings settings;

    private AbsLogStyle style;

    private ILog logImp;

    private boolean hasCustomTag = true;

    public LogPrintHelper(LogSettings settings, AbsLogStyle style, ILog imp) {
        this.settings = settings;
        this.style = style;
        sb = new StringBuilder();
        logImp = imp;
        style.setHelper(this);
    }

    /**
     * @param element 当前栈，可为空
     */
    public String getAutoTag(@Nullable StackTraceElement element) {
        hasCustomTag = false;
        final String tag;

        if (settings.globalTag != null) {
            // force tag
            tag = settings.globalTag;
        } else {
            if (element == null) {
                final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
                int offset = getStackOffsetByClz(stackTrace, stackTrace[1].getClassName()) + settings.methodOffset + 2;
                tag = getStackElementTag(stackTrace.length > offset ? stackTrace[offset] : stackTrace[stackTrace.length - 1]);
            } else {
                tag = getStackElementTag(element);
            }
        }
        return TextUtils.isEmpty(settings.tagPrefix) ? tag : settings.tagPrefix + "-" + tag;
    }

    private static String getStackElementTag(StackTraceElement element) {
        String tag = element.getClassName();
        Matcher m = ANONYMOUS_CLASS.matcher(tag);
        if (m.find()) {
            tag = m.replaceAll("");
        }
        tag = tag.substring(tag.lastIndexOf('.') + 1);
        return tag.length() > MAX_TAG_LENGTH ? tag.substring(0, MAX_TAG_LENGTH) : tag;
    }

    /**
     * 通过查找调用Class来确定堆栈的偏移量
     */
    private int getStackOffsetByClz(StackTraceElement[] trace, String clzName) {
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(clzName)) {
                return --i;
            }
        }
        return -1;
    }

    public void printLog(int priority, String tag, String message, Throwable t) {
        handleStyle(message);

        message = sb.toString();

        if (message.length() < MAX_LOG_LENGTH) {
            logImp.println(priority, tag, message, t);
            hasCustomTag = true;
            return;
        }

        for (int i = 0, length = message.length(); i < length; i++) {
            int newline = message.indexOf('\n', i);
            newline = newline != -1 ? newline : length;
            do {
                int end = Math.min(newline, i + MAX_LOG_LENGTH);
                String part = message.substring(i, end);
                logImp.println(priority, tag, "\n" + part, t);
                i = end;
            } while (i < newline);
        }
        hasCustomTag = true;
    }

    private void handleStyle(@NonNull String message) {
        sb = new StringBuilder();

        if (style == null) {
            return;
        }
        sb.append(style.getMsgHeader() == null ? "" : style.getMsgHeader());
        String[] lines = message.split(PROPERTY);
        for (int i = 0, length = lines.length; i < length; i++) {
            sb.append(style.getMsgLine(lines[i], i, length)).append("\n");
        }
        sb.append(style.getMsgFooter() == null ? "" : style.getMsgFooter());
    }

    public boolean isLoggable(int priority, String tag) {
        return priority >= settings.priority;
    }

    public LogSettings getSettings() {
        return settings;
    }

    public boolean hasCustomTag() {
        return hasCustomTag;
    }

    public interface ILog {

        void println(int priority, String tag, String message, Throwable throwable);
    }
}
