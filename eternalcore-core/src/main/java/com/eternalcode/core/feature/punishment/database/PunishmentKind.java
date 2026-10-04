package com.eternalcode.core.feature.punishment.database;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Discriminator of a row in the punishments table. Internal to the storage layer:
 * domains (Ban, Mute, ...) are modelled as separate types, this enum only tells
 * repositories how to (de)serialize a row. A revocation (unban, unmute, ...) is stored
 * as its own row of the matching REVOKED_* kind, so the table doubles as the history log.
 */
public enum PunishmentKind {

    WARN,
    REVOKED_WARN,
    BAN,
    REVOKED_BAN,
    IP_BAN,
    REVOKED_IP_BAN,
    MUTE,
    REVOKED_MUTE,
    KICK,
    KICK_ALL;

    private static final Set<PunishmentKind> PUNISHMENTS = Arrays.stream(values())
        .filter(kind -> !kind.isRevocation())
        .collect(Collectors.toUnmodifiableSet());

    public static Set<PunishmentKind> punishments() {
        return PUNISHMENTS;
    }

    public boolean isRevocation() {
        return switch (this) {
            case REVOKED_WARN, REVOKED_BAN, REVOKED_IP_BAN, REVOKED_MUTE -> true;
            default -> false;
        };
    }

    public boolean isRevocable() {
        return switch (this) {
            case WARN, BAN, IP_BAN, MUTE -> true;
            default -> false;
        };
    }

    public PunishmentKind revocation() {
        return switch (this) {
            case WARN -> REVOKED_WARN;
            case BAN -> REVOKED_BAN;
            case IP_BAN -> REVOKED_IP_BAN;
            case MUTE -> REVOKED_MUTE;
            default -> throw new IllegalStateException(this + " cannot be revoked");
        };
    }
}
