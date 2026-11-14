package com.eternalcode.core.feature.kit;

public enum KitClaimStatus {

    SUCCESS,
    NO_PERMISSION,
    ON_COOLDOWN,
    CANCELLED,
    /**
     * Player's cooldowns are still being loaded from the database (right after join) or loading failed.
     */
    COOLDOWNS_NOT_LOADED

}
