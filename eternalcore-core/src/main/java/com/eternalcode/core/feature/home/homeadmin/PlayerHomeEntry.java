package com.eternalcode.core.feature.home.homeadmin;

import com.eternalcode.core.feature.home.Home;
import org.bukkit.entity.Player;

public record PlayerHomeEntry(Player player, Home home) {
}
