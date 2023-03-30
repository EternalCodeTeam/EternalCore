package com.eternalcode.core.feature.essentials.mob;

public enum MobType {
    PASSIVE(true, true),
    AGGRESSIVE(true, true),
    OTHER(false, false),
    UNDEFINED(false, false),
    ALL(true, true);

    private final boolean isParseable;
    private final boolean isSuggeestable;

    MobType(boolean isParseable, boolean isSuggeestable) {
        this.isParseable = isParseable;
        this.isSuggeestable = isSuggeestable;
    }

    public boolean isParseable() {
        return this.isParseable;
    }

    public boolean isSuggeestable() {
        return this.isSuggeestable;
    }
}
