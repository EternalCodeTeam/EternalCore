package com.eternalcode.core;

import com.google.common.base.Stopwatch;
import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

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
        Environment environment = PaperLib.getEnvironment();

        if (!environment.isSpigot()) {
            this.logger.warning("Your server is running on unsupported software, please use Spigot/Paper or their other 1.20+ forks");
            this.logger.warning("We recommend using Paper, download it from https://papermc.io/downloads");
            this.logger.warning("WARNING: Supported Minecraft versions are 1.17-1.20x");
            return;
        }

        if (!environment.isVersion(17)) {
            this.logger.warning("EternalCore no longer supports your version, be aware that there may be bugs!");
            return;
        }

        this.logger.info("Your server is running on supported software. Congratulations!");
        this.logger.info("Server version: " + environment.getMinecraftVersion());
    }

}
