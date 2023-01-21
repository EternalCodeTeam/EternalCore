package com.eternalcode.core.afk;

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
        return afkReason;
    }

    public Instant getStart() {
        return start;
    }

    public UUID getPlayer() {
        return player;
    }

}
