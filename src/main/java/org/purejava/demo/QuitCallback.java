package org.purejava.demo;

import org.purejava.appindicator.GCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuitCallback implements GCallback.Function {
    private static final Logger LOG = LoggerFactory.getLogger(QuitCallback.class);
    @Override
    public void apply() {
        LOG.info("Hit Quit");
        System.exit(0);
    }
}
