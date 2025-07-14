package com.eternalcode.core.feature.vanish;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface VanishService {

    void enableVanish(Player player);

    void disableVanish(Player player);

    boolean isVanished(Player player);

    boolean isVanished(UUID uniqueId);

    void hideAdminForPlayer(Player player);

}
