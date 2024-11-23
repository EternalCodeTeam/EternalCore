package com.eternalcode.core.feature.randomteleport;

import com.eternalcode.core.placeholder.Placeholders;
import org.bukkit.entity.Player;

final class RandomTeleportPlaceholders {

    static final Placeholders<Player> PLACEHOLDERS = Placeholders.<Player>builder()
        .with("{PLAYER}", Player::getName)
        .with("{WORLD}", player -> player.getWorld().getName())
        .with("{X}", player -> String.valueOf(player.getLocation().getBlockX()))
        .with("{Y}", player -> String.valueOf(player.getLocation().getBlockY()))
        .with("{Z}", player -> String.valueOf(player.getLocation().getBlockZ()))
        .build();

}
