package com.eternalcode.core.feature.privatechat.toggle;

import java.util.UUID;

/**
 * Represents a player's private chat state.
 */
public class PrivateChatToggle {

    private final UUID uuid;
    private final PrivateChatState state;

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
