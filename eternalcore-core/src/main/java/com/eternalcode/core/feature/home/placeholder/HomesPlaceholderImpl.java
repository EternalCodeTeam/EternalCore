package com.eternalcode.core.feature.home.placeholder;

import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.stream.Collectors;

public class HomesPlaceholderImpl implements PlaceholderReplacer {

    private final HomeManager homeManager;

    public HomesPlaceholderImpl(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Override
    public String apply(String text, Player targetPlayer) {
        Collection<Home> homes = this.homeManager.getHomes(targetPlayer.getUniqueId());

        if (homes.isEmpty()) {
            return "You don't have any home.";
        }

        return homes.stream().map(Home::getName).collect(Collectors.joining(", "));
    }
}
