package com.eternalcode.core.feature.punishment.gui;

import com.eternalcode.core.feature.punishment.Punishment;
import com.eternalcode.core.feature.punishment.ban.Ban;
import com.eternalcode.core.feature.punishment.database.PunishmentKind;
import com.eternalcode.core.feature.punishment.ipban.IpBan;
import com.eternalcode.core.feature.punishment.kick.Kick;
import com.eternalcode.core.feature.punishment.mute.Mute;
import com.eternalcode.core.feature.punishment.warn.Warn;

import java.util.Set;

public enum PunishmentHistoryFilter {

    ALL(PunishmentKind.punishments()),
    BAN(Set.of(PunishmentKind.BAN)),
    BAN_IP(Set.of(PunishmentKind.IP_BAN)),
    MUTE(Set.of(PunishmentKind.MUTE)),
    WARN(Set.of(PunishmentKind.WARN)),
    KICK(Set.of(PunishmentKind.KICK, PunishmentKind.KICK_ALL));

    private static final PunishmentHistoryFilter[] CYCLE = values();

    private final Set<PunishmentKind> kinds;

    PunishmentHistoryFilter(Set<PunishmentKind> kinds) {
        this.kinds = Set.copyOf(kinds);
    }

    /**
     * @return the filter that matches only the domain of the given punishment (never {@link #ALL})
     */
    public static PunishmentHistoryFilter of(Punishment punishment) {
        return switch (punishment) {
            case Ban ban -> BAN;
            case IpBan ipBan -> BAN_IP;
            case Mute mute -> MUTE;
            case Warn warn -> WARN;
            case Kick kick -> KICK;
            default -> throw new IllegalArgumentException("Unsupported punishment domain: " + punishment.getClass().getName());
        };
    }

    Set<PunishmentKind> kinds() {
        return this.kinds;
    }

    PunishmentHistoryFilter next() {
        return CYCLE[(this.ordinal() + 1) % CYCLE.length];
    }
}
