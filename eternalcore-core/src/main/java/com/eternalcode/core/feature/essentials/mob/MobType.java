package com.eternalcode.core.feature.essentials.mob;

enum MobType {

    PASSIVE(true, true),
    AGGRESSIVE(true, true),
    OTHER(false, false),
    ALL(true, true);

    private final boolean isParseable;
    private final boolean isSuggeestable;

    MobType(boolean isParseable, boolean isSuggeestable) {
        this.isParseable = isParseable;
        this.isSuggeestable = isSuggeestable;
    }

    boolean isParseable() {
        return this.isParseable;
    }

    boolean isSuggeestable() {
        return this.isSuggeestable;
    }
}
