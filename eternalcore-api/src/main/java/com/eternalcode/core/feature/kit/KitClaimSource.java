package com.eternalcode.core.feature.kit;

public enum KitClaimSource {

    /**
     * Claimed by the player with a command or the GUI.
     */
    PLAYER,

    /**
     * Given by an administrator, without checks and without recording a claim.
     */
    ADMIN,

    /**
     * Given automatically on the player's first join.
     */
    FIRST_JOIN

}
