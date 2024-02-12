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
}
