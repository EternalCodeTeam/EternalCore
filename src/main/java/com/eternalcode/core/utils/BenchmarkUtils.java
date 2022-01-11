/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class BenchmarkUtils {

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

    public static String cpuName() {
        return System.getenv("PROCESSOR_IDENTIFIER");
    }

    public static String getTPS() {
        return Arrays.stream(Bukkit.getTPS()).mapToObj(BenchmarkUtils::tpsFormat).collect(Collectors.joining(", "));
    }

    // TODO: MOVE COLORS TO CONFIG
    private static String tpsFormat(double tps) {
        return ((tps > 18.0) ? ChatColor.GREEN : (tps > 16.0) ? ChatColor.YELLOW : ChatColor.RED)
            + ((tps > 20.0) ? "*" : "") + Math.min(Math.round(tps * 100.0) / 100.0, 20.0);
    }

    private BenchmarkUtils(){
    }
}
