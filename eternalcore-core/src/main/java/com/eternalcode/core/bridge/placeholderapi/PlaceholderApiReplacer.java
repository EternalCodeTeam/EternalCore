package com.eternalcode.core.bridge.placeholderapi;


import com.eternalcode.core.placeholder.PlaceholderReplacer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PlaceholderApiReplacer implements PlaceholderReplacer {

    @Override
    public String apply(String text, Player targetPlayer) {
        return PlaceholderAPI.setPlaceholders(targetPlayer, text);
    }


}
