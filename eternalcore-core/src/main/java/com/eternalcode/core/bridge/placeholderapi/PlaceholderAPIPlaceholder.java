package com.eternalcode.core.bridge.placeholderapi;


import com.eternalcode.core.placeholder.Placeholder;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PlaceholderAPIPlaceholder implements Placeholder {

    @Override
    public String apply(String text, Player targetPlayer) {
        return PlaceholderAPI.setPlaceholders(targetPlayer, text);
    }


}
