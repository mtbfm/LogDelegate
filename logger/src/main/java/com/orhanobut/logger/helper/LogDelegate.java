package com.orhanobut.logger.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Kale
 * @date 2017/9/28
 */
public class LogDelegate {

    public static final int BASE_STACK_OFFSET = 10;

    public static final int MIN_STACK_OFFSET = 5;

    private static final int MAX_LOG_LENGTH = 4000;

    private static final int MAX_TAG_LENGTH = 23;

    private static final String PROPERTY = System.getProperty("line.separator");

    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");

    private StringBuilder sb;

    private final LogSettings settings;

    private AbsLogFormatter format;

    private ILogPrinter printer;

    private boolean hasCustomTag = true;

    public LogDelegate(LogSettings logSettings, AbsLogFormatter logFormatter, ILogPrinter logPrinter) {
        settings = logSettings;
        format = logFormatter;
        printer = logPrinter;
        sb = new StringBuilder();

        format.setDelegate(this);
    }

    /**
     * @param element 当前栈，可为空
     */
    public String getAutoTag(@Nullable StackTraceElement element) {
        hasCustomTag = false;
        final String tag;

        if (!isEmpty(settings.globalTag)) {
            // force tag
            tag = settings.globalTag;
        } else {
            if (element == null) {
                final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
                int offset = getStackOffsetByClz(stackTrace, stackTrace[1].getClassName()) + settings.methodOffset + 2;
                tag = getTagByStack(stackTrace.length > offset ? stackTrace[offset] : stackTrace[stackTrace.length - 1]);
            } else {
                tag = getTagByStack(element);
            }
        }
        return isEmpty(settings.tagPrefix) ? tag : settings.tagPrefix + "-" + tag;
    }

    private static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    private static String getTagByStack(StackTraceElement element) {
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
                return --i; // 一般情况下是相等的，这里处理tag的问题所以-1
            }
        }
        return -1;
    }

    /**
     * 输出log信息
     */
    public void printLog(int priority, String tag, String message, Throwable t) {
        // 1. 样式美化
        message = formatMessage(message, sb).toString();
        sb.setLength(0);

        // 2. 进行输出
        if (message.length() < MAX_LOG_LENGTH) {
            // 不是超长log，不用换行，直接进行输出
            printer.println(priority, tag, message, t);
        } else {
            for (int i = 0, length = message.length(); i < length; i++) {
                int newline = message.indexOf('\n', i);
                newline = newline != -1 ? newline : length;
                do {
                    int end = Math.min(newline, i + MAX_LOG_LENGTH);
                    String part = message.substring(i, end);
                    printer.println(priority, tag, "\n" + part, t);
                    i = end;
                } while (i < newline);
            }
        }

        hasCustomTag = true;
    }

    private StringBuilder formatMessage(@NonNull String message, StringBuilder sb) {
        if (format == null) {
            return sb;
        }
        sb.append(format.getMsgHeader() == null ? "" : format.getMsgHeader());
        String[] lines = message.split(PROPERTY);
        for (int i = 0, length = lines.length; i < length; i++) {
            sb.append(format.getFormatMsgLine(lines[i], i, length)).append("\n");
        }
        sb.append(format.getMsgFooter() == null ? "" : format.getMsgFooter());
        return sb;
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

}
