package com.eternalcode.core.feature.deathteleport;

public enum DeathTeleportState {
    ENABLED,
    DISABLED;


    DeathTeleportState invert() {
        return this == ENABLED ? DISABLED : ENABLED;
    }
}
