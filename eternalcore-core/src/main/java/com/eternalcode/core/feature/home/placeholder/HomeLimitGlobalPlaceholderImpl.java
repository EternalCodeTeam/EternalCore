package com.eternalcode.core.feature.home.placeholder;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import org.bukkit.entity.Player;

public class HomeLimitGlobalPlaceholderImpl implements PlaceholderReplacer {

    private final PluginConfiguration pluginConfiguration;

    public HomeLimitGlobalPlaceholderImpl(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    @Override
    public String apply(String text, Player targetPlayer) {
        int allowedHomes = this.pluginConfiguration.homes.maxHomes.values()
            .stream()
            .max(Integer::compare)
            .orElse(0);

        return String.valueOf(allowedHomes);
    }
}
