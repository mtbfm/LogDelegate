package com.orhanobut.logger.helper;

import android.support.annotation.Nullable;

/**
 * @author Kale
 * @date 2017/9/28
 *
 * 针对log进行样式的处理
 */
public abstract class AbsLogStyle {

    protected LogPrintHelper helper;

    void setHelper(LogPrintHelper helper) {
        this.helper = helper;
    }

    protected LogSettings getSettings() {
        return helper.getSettings();
    }

    /**
     * 可被复写用来永远返回false
     */
    protected boolean hasCustomTag() {
        return helper.hasCustomTag();
    }

    @Nullable
    protected abstract String getMsgHeader();

    @Nullable
    protected abstract String getMsgFooter();

    @Nullable
    protected abstract String getMsgLine(String message, int line, int lineCount);
}
