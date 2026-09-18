package com.eternalcode.core.feature.lag;

import java.util.Locale;

public final class LagStatsUtil {

    public static final double TARGET_TPS = 20.0;

    private static final double TPS_GOOD_THRESHOLD = 18.0;
    private static final double TPS_MEDIUM_THRESHOLD = 15.0;
    private static final double BYTES_IN_MEGABYTE = 1024.0 * 1024.0;

    private LagStatsUtil() {
        throw new UnsupportedOperationException("Cannot instantiate utility class");
    }

    public static String formatTps(double tps) {
        double normalized = Math.min(tps, TARGET_TPS);
        return colorFor(normalized) + String.format(Locale.ROOT, "%.2f", normalized);
    }

    public static String formatMspt(double mspt) {
        double equivalentTps = Math.min(TARGET_TPS, 1000.0 / mspt);
        return colorFor(equivalentTps) + String.format(Locale.ROOT, "%.2f", mspt) + "ms";
    }

    public static String colorFor(double tps) {
        if (tps >= TPS_GOOD_THRESHOLD) {
            return "<green>";
        }

        if (tps >= TPS_MEDIUM_THRESHOLD) {
            return "<yellow>";
        }

        return "<red>";
    }

    public static String formatMemoryMb(long bytes) {
        return String.format(Locale.ROOT, "%.0f", bytes / BYTES_IN_MEGABYTE);
    }

    public static String formatWorldEntry(String template, WorldStats stats) {
        if (template == null) {
            throw new IllegalArgumentException("Template cannot be null");
        }

        if (stats == null) {
            throw new IllegalArgumentException("WorldStats cannot be null");
        }

        return template
            .replace("{WORLD}", stats.name())
            .replace("{CHUNKS}", String.valueOf(stats.chunks()))
            .replace("{ENTITIES}", String.valueOf(stats.entities()))
            .replace("{TILE-ENTITIES}", String.valueOf(stats.tileEntities()))
            .replace("{PLAYERS}", String.valueOf(stats.players()));
    }
}
