/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.utils;

import org.bukkit.Bukkit;

public final class BenchmarkUtils {
    public static long getUsedMemory() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L;
    }

    public static double[] getTPS() {
        return Bukkit.getTPS();
    }
}
