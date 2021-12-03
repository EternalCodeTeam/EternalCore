package com.eternalcode.core.utils;

import com.eternalcode.core.EternalCore;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public final class PlaceholderUtils {
    public static String parsePlaceholders(Player player, String string) {
        if (EternalCore.getInstance().getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            return ChatUtils.color(PlaceholderAPI.setPlaceholders(player, string));
        } else {
            return ChatUtils.color(string);
        }
    }
}
