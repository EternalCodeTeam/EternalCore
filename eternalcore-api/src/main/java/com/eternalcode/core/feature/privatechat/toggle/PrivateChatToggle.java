package com.eternalcode.core.feature.privatechat.toggle;

import java.util.UUID;

/**
 * Represents a player's private chat toggle state.
 */
public class PrivateChatToggle {

    UUID uuid;
    PrivateChatToggleState state;

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
