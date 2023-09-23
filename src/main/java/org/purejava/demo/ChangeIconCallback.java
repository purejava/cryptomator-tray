package org.purejava.demo;

import org.purejava.appindicator.GCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static org.purejava.appindicator.app_indicator_h.app_indicator_set_icon;

public class ChangeIconCallback implements GCallback {
    private static final Logger LOG = LoggerFactory.getLogger(ChangeIconCallback.class);
    private MemorySegment indicator;
    public ChangeIconCallback(MemorySegment indicator) {
        this.indicator = indicator;
    }
    @Override
    public void apply() {
        try (var arena = Arena.ofConfined()) {
            LOG.info("Changing icon ...");
            app_indicator_set_icon(indicator, arena.allocateUtf8String("/home/ralph/IdeaProjects/cryptomator/dist/linux/common/org.cryptomator.Cryptomator.tray.svg"));
        }
    }
}
