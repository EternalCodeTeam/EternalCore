package com.eternalcode.core.feature.jail;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.UUID;

public class Prisoner {

    private final UUID player;
    private final Instant prison_time;
    private final String reason;
    private final UUID lockedUpByWho;


    public Prisoner(UUID player, Instant prisonTime, String reason, UUID lockedUpByWho) {
        this.player = player;
        this.prison_time = prisonTime;
        this.reason = reason;
        this.lockedUpByWho = lockedUpByWho;
    }

    public UUID getPlayer() {
        return player;
    }

    public Instant getPrisonTime() {
        return prison_time;
    }

    public String getReason() {
        return reason;
    }

    public UUID getLockedUpByWho() {
        return lockedUpByWho;
    }

    public boolean isLockedUp() {
        return prison_time.isAfter(Instant.now());
    }



}
