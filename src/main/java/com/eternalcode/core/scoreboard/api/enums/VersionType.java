package com.eternalcode.core.scoreboard.api.enums;

public enum VersionType {
    V1_7,
    V1_8,
    V1_13,
    V1_17;

    public boolean isHigherOrEqual() {
        return ordinal() >= ordinal();
    }
}
