package com.orhanobut.logger.helper;

import android.support.annotation.Nullable;

/**
 * @author Kale
 * @date 2017/9/28
 *
 * 针对log进行样式的处理
 */
public abstract class AbsLogFormatter {

    protected LogPrintDelegate delegate;

    void setDelegate(LogPrintDelegate delegate) {
        this.delegate = delegate;
    }

    protected LogSettings getSettings() {
        return delegate.getSettings();
    }

    /**
     * 可被复写用来永远返回false
     */
    protected boolean hasCustomTag() {
        return delegate.hasCustomTag();
    }

    @Nullable
    protected abstract String getMsgHeader();

    @Nullable
    protected abstract String getMsgFooter();

    @Nullable
    protected abstract String getFormatMsgLine(String message, int line, int lineCount);
}
