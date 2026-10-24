package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.ban.Ban;
import com.eternalcode.core.feature.punishment.PunishmentType;
import com.eternalcode.core.feature.punishment.banip.BanIp;
import com.eternalcode.core.feature.punishment.kick.Kick;
import com.eternalcode.core.feature.punishment.mute.Mute;
import com.eternalcode.core.feature.punishment.warn.Warn;

import java.util.Set;

public enum PunishmentHistoryFilter {

    ALL(PunishmentType.punishments()),
    BAN(Set.of(PunishmentType.BAN)),
    BAN_IP(Set.of(PunishmentType.IP_BAN)),
    MUTE(Set.of(PunishmentType.MUTE)),
    WARN(Set.of(PunishmentType.WARN)),
    KICK(Set.of(PunishmentType.KICK, PunishmentType.KICK_ALL));

    private static final PunishmentHistoryFilter[] CYCLE = values();

    private final Set<PunishmentType> kinds;

    PunishmentHistoryFilter(Set<PunishmentType> kinds) {
        this.kinds = Set.copyOf(kinds);
    }

    /**
     * @return the filter that matches only the domain of the given punishment (never {@link #ALL})
     */
    public static PunishmentHistoryFilter of(Punishment punishment) {
        return switch (punishment) {
            case Ban ban -> BAN;
            case BanIp banIp -> BAN_IP;
            case Mute mute -> MUTE;
            case Warn warn -> WARN;
            case Kick kick -> KICK;
            default -> throw new IllegalArgumentException("Unsupported punishment domain: " + punishment.getClass().getName());
        };
    }

    Set<PunishmentType> kinds() {
        return this.kinds;
    }

    PunishmentHistoryFilter next() {
        return CYCLE[(this.ordinal() + 1) % CYCLE.length];
    }
}
