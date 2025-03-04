package com.eternalcode.core.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class CompletableFutureUtil {

    private static final Logger LOGGER = Logger.getLogger(CompletableFutureUtil.class.getName());

    private CompletableFutureUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static <T> T handleCaughtException(Throwable cause) {
        LOGGER.log(Level.SEVERE, "Caught an exception in future execution: {0}", cause);
        return null;
    }
}
