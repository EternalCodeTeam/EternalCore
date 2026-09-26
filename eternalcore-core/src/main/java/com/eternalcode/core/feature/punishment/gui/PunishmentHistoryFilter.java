package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.PunishmentType;

import java.util.Set;

public enum PunishmentHistoryFilter {

    ALL(PunishmentType.values()),
    BAN(PunishmentType.BAN),
    BAN_IP(PunishmentType.BAN_IP),
    MUTE(PunishmentType.MUTE),
    WARN(PunishmentType.WARN),
    KICK(PunishmentType.KICK);

    private static final PunishmentHistoryFilter[] CYCLE = values();

    private final Set<PunishmentType> types;

    PunishmentHistoryFilter(PunishmentType... types) {
        this.types = Set.of(types);
    }

    Set<PunishmentType> types() {
        return this.types;
    }

    PunishmentHistoryFilter next() {
        return CYCLE[(this.ordinal() + 1) % CYCLE.length];
    }
}
