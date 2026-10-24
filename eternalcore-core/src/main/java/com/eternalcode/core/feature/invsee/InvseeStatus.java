package com.eternalcode.core.feature.invsee;

public enum InvseeStatus {
    /**
     * Nobody is changing the inventory, inspectors can modify it.
     */
    FREE,
    /**
     * An inspector's change is waiting to be written - the target and other inspectors can't modify the inventory.
     */
    BLOCKED
}
