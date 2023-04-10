package com.eternalcode.core.bridge.placeholderapi;

import com.eternalcode.core.placeholder.PlayerPlaceholderReplacer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PlaceholderApiReplacer implements PlayerPlaceholderReplacer {

    @Override
    public String apply(String text, Player targetPlayer) {
        return PlaceholderAPI.setPlaceholders(targetPlayer, text);
    }

}
