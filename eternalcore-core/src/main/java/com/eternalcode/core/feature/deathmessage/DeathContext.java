package com.eternalcode.core.feature.deathmessage;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.jspecify.annotations.Nullable;

public record DeathContext(
    Player victim,
    @Nullable Player killer,
    @Nullable Entity damager,
    @Nullable DamageCause cause,
    DeathType deathType
) {

    public boolean isKilledByPlayer() {
        return this.deathType == DeathType.PLAYER_KILL;
    }

    public enum DeathType {
        PLAYER_KILL,
        ENTITY_KILL,
        ENVIRONMENT,
        UNKNOWN
    }
}
