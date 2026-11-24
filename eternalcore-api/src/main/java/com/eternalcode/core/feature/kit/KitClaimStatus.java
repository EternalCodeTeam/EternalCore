package com.eternalcode.core.feature.kit;

public enum KitClaimStatus {

    SUCCESS,
    NO_PERMISSION,
    ON_COOLDOWN,

    /**
     * A {@link KitType#ONE_TIME} or {@link KitType#FIRST_JOIN} kit was already claimed.
     */
    ALREADY_CLAIMED,
    WRONG_WORLD,
    WRONG_GAME_MODE,
    NOT_ENOUGH_PLAYTIME,

    /**
     * The items do not fit into the inventory and full-inventory behavior is set to cancel.
     */
    INVENTORY_FULL,

    /**
     * Another claim of the same player is in progress.
     */
    ALREADY_CLAIMING,

    /**
     * Cancelled by a {@link com.eternalcode.core.feature.kit.event.PreKitClaimEvent} listener.
     */
    CANCELLED,

    /**
     * The player's kit state is still being loaded from the database (right after join) or loading failed.
     */
    DATA_NOT_LOADED

}
