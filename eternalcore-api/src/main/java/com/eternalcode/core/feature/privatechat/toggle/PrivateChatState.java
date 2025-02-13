package com.eternalcode.core.feature.privatechat.toggle;

/**
 * Enum representing state of blocking incoming private messages by the player.
 */
public enum PrivateChatState {

    /**
     * Player can receive private messages.
     */
    ENABLE,

    /**
     * Player cannot receive private messages.
     */
    DISABLE;

    PrivateChatState invert() {
        return this == ENABLE ? DISABLE : ENABLE;
    }

}
