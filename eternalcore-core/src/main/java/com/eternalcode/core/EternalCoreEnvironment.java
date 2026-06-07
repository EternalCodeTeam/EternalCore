package com.eternalcode.core;

import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.bukkit.Bukkit;

class EternalCoreEnvironment {

    private final Logger logger;
    private final Stopwatch stopwatch = Stopwatch.createStarted();

    EternalCoreEnvironment(Logger logger) {
        this.logger = logger;
        this.checkSoftware();
    }

    void initialize() {
        long millis = this.stopwatch.elapsed(TimeUnit.MILLISECONDS);
        this.logger.info("Successfully loaded EternalCore in " + millis + "ms");
    }

    private void checkSoftware() {
        this.logger.info("Your server is running on supported Paper API software");
        this.logger.info("Server version: " + Bukkit.getBukkitVersion());
    }

}
