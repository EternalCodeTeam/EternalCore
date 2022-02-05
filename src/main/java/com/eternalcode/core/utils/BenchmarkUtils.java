/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.utils;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.PluginConfiguration;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class BenchmarkUtils {

    private static ConfigurationManager configurationManager;

    public BenchmarkUtils(ConfigurationManager configurationManager) {
        BenchmarkUtils.configurationManager = configurationManager;
    }

    public static long getUsedMemory() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L;
    }

    public static long maxMemory() {
        return Runtime.getRuntime().maxMemory() / 1024 / 1024L;
    }

    public static long totalMemory() {
        return Runtime.getRuntime().totalMemory() / 1024 / 1024L;
    }

    public static long freeMemory() {
        return Runtime.getRuntime().freeMemory() / 1024 / 1024L;
    }

    public static long cpuCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static String getTPS() {
        return Arrays.stream(Bukkit.getTPS()).mapToObj(BenchmarkUtils::tpsFormat).collect(Collectors.joining(", "));
    }

    private static String tpsFormat(double tps) {
        PluginConfiguration config = configurationManager.getPluginConfiguration();

        return ((tps > 18.0) ? config.flawlessTPS : (tps > 16.0) ? config.fairTPS : config.poorTPS)
            + ((tps > 20.0) ? "*" : "") + Math.min(Math.round(tps * 100.0) / 100.0, 20.0);
    }
}
