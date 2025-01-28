package com.eternalcode.core.feature.privatechat.toggle;

import java.util.UUID;

public class PrivateChatToggle {

    UUID uuid;
    PrivateChatToggleState state;

    public PrivateChatToggle() {

    }

    public PrivateChatToggle(UUID uuid, PrivateChatToggleState toggle) {
        this.uuid = uuid;
        this.state = toggle;
    }

    public UUID getUuid() {
        return uuid;
    }

    public PrivateChatToggleState getState() {
        return state;
    }
}
