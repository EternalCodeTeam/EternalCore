package com.eternalcode.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class TPSUtils {
    public static String getTPS() {
        return Arrays.stream(Bukkit.getTPS()).mapToObj(TPSUtils::format).collect(Collectors.joining(", "));
    }


    // TODO: MOVE COLORS TO CONFIG
    private static String format(double tps) {
        return ((tps > 18.0) ? ChatColor.GREEN : (tps > 16.0) ? ChatColor.YELLOW : ChatColor.RED)
            + ((tps > 20.0) ? "*" : "") + Math.min(Math.round(tps * 100.0) / 100.0, 20.0);
    }
}
