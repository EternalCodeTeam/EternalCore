package com.eternalcode.core.feature.home.placeholder;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import org.bukkit.entity.Player;

public class HomesLimitPlaceholderImpl implements PlaceholderReplacer {

    private final HomeManager homeManager;
    private final PluginConfiguration pluginConfiguration;

    public HomesLimitPlaceholderImpl(HomeManager homeManager, PluginConfiguration pluginConfiguration) {
        this.homeManager = homeManager;
        this.pluginConfiguration = pluginConfiguration;
    }

    @Override
    public String apply(String text, Player targetPlayer) {
        int maxHomes = this.homeManager.getMaxAmountOfHomes(targetPlayer, this.pluginConfiguration.homes.maxHomes);
        return String.valueOf(maxHomes);
    }
}
