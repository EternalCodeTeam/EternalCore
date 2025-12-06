package com.eternalcode.core.feature.home.homeadmin;

import com.eternalcode.core.feature.home.Home;
import org.bukkit.OfflinePlayer;

public record PlayerHomeEntry(OfflinePlayer offlinePlayer, Home home) {
}
