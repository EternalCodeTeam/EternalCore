package com.eternalcode.core.utils;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.chat.ChatUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public final class PlaceholderUtils {
    // TODO: Delete cringe temporary PlaceholderAPI
    public static String parsePlaceholders(Player player, String string) {
        if (EternalCore.getInstance().getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            return ChatUtils.color(PlaceholderAPI.setPlaceholders(player, string));
        }
        return ChatUtils.color(string);
    }
}
