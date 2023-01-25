package com.eternalcode.core.feature.afk;

import java.time.Instant;
import java.util.UUID;

public class Afk {

    private final UUID player;
    private final AfkReason afkReason;
    private final Instant start;

    Afk(UUID player, AfkReason afkReason, Instant start) {
        this.player = player;
        this.afkReason = afkReason;
        this.start = start;
    }

    public AfkReason getAfkReason() {
        return this.afkReason;
    }

    public Instant getStart() {
        return this.start;
    }

    public UUID getPlayer() {
        return this.player;
    }

}
