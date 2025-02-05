package com.eternalcode.core.feature.privatechat.toggle;

import java.util.UUID;

/**
 * Represents a player's private chat toggle state.
 */
public class PrivateChatToggle {

    UUID uuid;
    PrivateChatState state;

    public PrivateChatToggle(UUID uuid, PrivateChatState toggle) {
        this.uuid = uuid;
        this.state = toggle;
    }

    public UUID getUuid() {
        return uuid;
    }

    public PrivateChatState getState() {
        return state;
    }
}
