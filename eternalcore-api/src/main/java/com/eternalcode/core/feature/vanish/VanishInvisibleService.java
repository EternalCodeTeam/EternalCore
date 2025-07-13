package com.eternalcode.core.feature.vanish;

import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public interface VanishInvisibleService {

    void hidePlayer(Player player);

    void showPlayer(Player player);

    void hideHiddenForPlayer(Player player);

    Set<UUID> getVanishedPlayers();
}
