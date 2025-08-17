package com.eternalcode.core.feature.freeze;

import org.bukkit.entity.Player;

import java.time.Duration;

public interface FreezeService {

    void freezePlayer(Player player, Duration duration);

    boolean unfreezePlayer(Player player);
}
