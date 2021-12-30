/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.utils;

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
}
