package com.eternalcode.core.feature.essentials.mob;

enum MobType {

    PASSIVE(true, true),
    AGGRESSIVE(true, true),
    VEHICLE(true, true),
    ARMOR_STAND(true, true),
    OTHER(true, false),
    ALL(true, true);

    private final boolean isParseable;
    private final boolean isSuggestable;

    MobType(boolean isParseable, boolean isSuggestible) {
        this.isParseable = isParseable;
        this.isSuggestable = isSuggestible;
    }

    boolean isParseable() {
        return this.isParseable;
    }

    boolean isSuggestable() {
        return this.isSuggestable;
    }
}
