package com.eternalcode.core.afk;

import com.eternalcode.core.publish.Content;

import java.util.UUID;

class AfkChangeEvent implements Content {

    private final UUID player;
    private final boolean afk;

    AfkChangeEvent(UUID player, boolean afk) {
        this.player = player;
        this.afk = afk;
    }

    public UUID getPlayer() {
        return player;
    }

    public boolean isAfk() {
        return afk;
    }

}
