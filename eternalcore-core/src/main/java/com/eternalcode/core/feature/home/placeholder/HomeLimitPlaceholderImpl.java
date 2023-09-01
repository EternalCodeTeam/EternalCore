package com.eternalcode.core.feature.home.placeholder;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import org.bukkit.entity.Player;

import java.util.Map;

public class HomeLimitPlaceholderImpl implements PlaceholderReplacer {

    private final PluginConfiguration pluginConfiguration;

    public HomeLimitPlaceholderImpl(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    @Override
    public String apply(String text, Player targetPlayer) {
        int allowedHomes = this.pluginConfiguration.homes.maxHomes.entrySet()
            .stream()
            .filter(entry -> targetPlayer.hasPermission(entry.getKey()))
            .map(Map.Entry::getValue)
            .max(Integer::compare)
            .orElse(0);

        return String.valueOf(allowedHomes);
    }
}
