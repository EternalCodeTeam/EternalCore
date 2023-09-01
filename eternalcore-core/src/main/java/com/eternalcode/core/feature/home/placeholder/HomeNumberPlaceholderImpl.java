package com.eternalcode.core.feature.home.placeholder;

import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import org.bukkit.entity.Player;

public class HomeNumberPlaceholderImpl implements PlaceholderReplacer {

    private final HomeManager homeManager;

    public HomeNumberPlaceholderImpl(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Override
    public String apply(String text, Player targetPlayer) {
        return String.valueOf(this.homeManager.getHomes(targetPlayer.getUniqueId()).size());
    }
}
