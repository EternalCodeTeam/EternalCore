package com.eternalcode.core.feature.kit;

public enum KitType {

    /**
     * Can be claimed repeatedly, every {@link Kit#cooldown()}.
     */
    RECURRING,

    /**
     * Can be claimed only once, until an administrator resets it.
     */
    ONE_TIME,

    /**
     * Given automatically when the player joins the server for the first time.
     * Behaves like {@link #ONE_TIME} when claimed manually (e.g. after a cancelled delivery).
     */
    FIRST_JOIN

}
