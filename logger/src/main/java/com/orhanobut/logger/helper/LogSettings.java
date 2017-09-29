package com.orhanobut.logger.helper;

import android.util.Log;

/**
 * @author Kale
 * @date 2016/3/27
 */
public class LogSettings {

    int methodOffset = 0;

    boolean showMethodLink = true;

    boolean showThreadInfo = false;

    String tagPrefix = null;

    String globalTag = null;

    int priority = Log.VERBOSE;

    public int getMethodOffset() {
        return methodOffset;
    }

    public boolean isShowMethodLink() {
        return showMethodLink;
    }

    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }

    public void setGlobalTag(String globalTag) {
        this.globalTag = globalTag;
    }

    public static LogSettings create() {
        return new LogSettings();
    }

    public static class Builder {

        private LogSettings settings = new LogSettings();

        public Builder methodOffset(int methodOffset) {
            settings.methodOffset = methodOffset;
            return this;
        }

        public Builder showThreadInfo(boolean showThreadInfo) {
            settings.showThreadInfo = showThreadInfo;
            return this;
        }

        public Builder showMethodLink(boolean showMethodLink) {
            settings.showMethodLink = showMethodLink;
            return this;
        }

        /**
         * @param priority one of
         *                 {@link Log#VERBOSE},
         *                 {@link Log#DEBUG},
         *                 {@link Log#INFO},
         *                 {@link Log#WARN},
         *                 {@link Log#ERROR}
         */
        public Builder logPriority(int priority) {
            settings.priority = priority;
            return this;
        }

        public Builder globalTag(String globalTag) {
            settings.globalTag = globalTag;
            return this;
        }

        public Builder tagPrefix(String prefix) {
            settings.tagPrefix = prefix;
            return this;
        }

        public LogSettings build() {
            return settings;
        }
    }

}
