package com.eternalcode.core.feature.msg.toggle;

/**
 * Enum representing state of blocking incoming private messages by the player.
 */
public enum MsgState {

    /**
     * Player can receive private messages.
     */
    ENABLED,

    /**
     * Player cannot receive private messages.
     */
    DISABLED;

    MsgState invert() {
        return this == ENABLED ? DISABLED : ENABLED;
    }

}
