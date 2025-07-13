package com.eternalcode.core.feature.vanish;

import org.bukkit.entity.Player;

public interface VanishService {

    void enableVanish(Player player);

    void disableVanish(Player player);

    boolean isVanished(Player player);

    void hideHiddenForPlayer(Player player);

}
