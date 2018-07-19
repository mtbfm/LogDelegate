package com.orhanobut.loggersample.tree;

import com.orhanobut.logger.helper.LogSettings;
import com.orhanobut.logger.helper.formatter.SimpleLogFormatter;

/**
 * extends {@link timber.log.Timber.Tree} for make log pretty
 */
public final class SimpleLogTree extends DefaultLogTree {

    public SimpleLogTree(LogSettings settings) {
        super(settings, new SimpleLogFormatter());
    }

}
