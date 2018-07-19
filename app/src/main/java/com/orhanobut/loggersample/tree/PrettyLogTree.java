package com.orhanobut.loggersample.tree;

import com.orhanobut.logger.helper.LogSettings;
import com.orhanobut.loggersample.format.PrettyFormatter;

/**
 * extends {@link timber.log.Timber.Tree} for make log pretty
 */
public final class PrettyLogTree extends DefaultLogTree {

    public PrettyLogTree(LogSettings settings) {
        super(settings, new PrettyFormatter());
    }

}
