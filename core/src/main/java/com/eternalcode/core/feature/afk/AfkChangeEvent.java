package com.eternalcode.core.feature.afk;

import com.eternalcode.core.publish.PublishContent;

import java.util.UUID;

class AfkChangeEvent implements PublishContent {

    private final UUID player;
    private final boolean afk;

    AfkChangeEvent(UUID player, boolean afk) {
        this.player = player;
        this.afk = afk;
    }

    public UUID getPlayer() {
        return this.player;
    }

    public boolean isAfk() {
        return this.afk;
    }

}
