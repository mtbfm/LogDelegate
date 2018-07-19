package com.orhanobut.logger.helper;

import android.support.annotation.Nullable;

/**
 * @author Kale
 * @date 2017/9/28
 *
 * 针对log进行样式的处理
 */
public abstract class AbsLogFormatter {

    private LogDelegate delegate;

    void setDelegate(LogDelegate delegate) {
        this.delegate = delegate;
    }

    protected LogSettings getSettings() {
        return delegate.getSettings();
    }

    protected LogDelegate getDelegate() {
        return delegate;
    }

    /**
     * 判断使用者是否用了自定义的tag
     * 
     * 可被复写用来永远返回false，那么就说明永远用自动填充的tag
     */
    protected boolean hasCustomTag() {
        return delegate.hasCustomTag();
    }

    /**
     * @return log打印时的头部内容
     */
    @Nullable
    protected abstract String getMsgHeader();

    /**
     * @return log打印时的底部内容
     */
    @Nullable
    protected abstract String getMsgFooter();

    /**
     * log的正文内容
     *
     * @param message   原始的内容
     * @param line      当前内容的行数，从0开始
     * @param lineCount log过长时会拆分为多行进行输出，这里是当前log的行数信息，通常为1行
     */
    @Nullable
    protected abstract String getFormatMsgLine(String message, int line, int lineCount);
    
}
