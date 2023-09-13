package com.eternalcode.core;

import com.google.common.base.Stopwatch;
import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

class EternalCoreEnvironment {

    private final EternalCoreApi eternalCoreApi;
    private final Logger logger;
    private final Stopwatch stopwatch = Stopwatch.createStarted();

    EternalCoreEnvironment(EternalCoreApi eternalCoreApi, Logger logger) {
        this.eternalCoreApi = eternalCoreApi;
        this.logger = logger;
        this.checkSoftware();
    }

    void initialize() {
        EternalCoreApiProvider.initialize(this.eternalCoreApi);

        long millis = this.stopwatch.elapsed(TimeUnit.MILLISECONDS);
        this.logger.info("Successfully loaded EternalCore in " + millis + "ms");
    }

    void shutdown() {
        EternalCoreApiProvider.deinitialize();
    }

    private void checkSoftware() {
        Environment environment = PaperLib.getEnvironment();

        if (!environment.isSpigot()) {
            this.logger.warning("Your server running on unsupported software, please use Spigot/Paper or their other 1.19+ forks");
            this.logger.warning("We recommend using paper, download paper from https://papermc.io/downloads");
            this.logger.warning("WARRING: Supported minecraft version is 1.17-1.19x");
            return;
        }

        if (!environment.isVersion(17)) {
            this.logger.warning("EternalCore no longer supports your version, be aware that there may be bugs!");
            return;
        }

        this.logger.info("Your server running on supported software, congratulations!");
        this.logger.info("Server version: " + environment.getMinecraftVersion());
    }

}
