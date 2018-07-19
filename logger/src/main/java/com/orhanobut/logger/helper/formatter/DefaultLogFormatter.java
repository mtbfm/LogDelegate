package com.orhanobut.logger.helper.formatter;

import android.support.annotation.Nullable;

import com.orhanobut.logger.helper.AbsLogFormatter;

/**
 * @author Kale
 * @date 2018/7/19
 */
public class DefaultLogFormatter extends AbsLogFormatter {

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
    protected String getFormatMsgLine(String message, int line, int lineCount) {
        return message;
    }

}
