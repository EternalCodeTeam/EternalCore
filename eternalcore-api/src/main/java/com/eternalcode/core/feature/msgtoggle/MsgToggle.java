package com.eternalcode.core.feature.msgtoggle;

import java.util.UUID;

public class MsgToggle {

    UUID uuid;
    boolean toggle;

    public MsgToggle() {

    }

    public MsgToggle(UUID uuid, boolean toggle) {
        this.uuid = uuid;
        this.toggle = toggle;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean getToggle() {
        return toggle;
    }
}
