package com.eternalcode.core.feature.punishment;

import java.time.Instant;

public enum PunishmentStatus {

    ACTIVE,
    EXPIRED,
    REVOKED,
    INSTANT;

    /**
     * Status of any punishment at the given moment: instantaneous punishments (kick) are always {@link #INSTANT}.
     */
    public static PunishmentStatus of(Punishment punishment, Instant now) {
        if (punishment instanceof ExpirablePunishment expirable) {
            return expirable.status(now);
        }

        return INSTANT;
    }

    /**
     * Status as it was at {@code now}: a revocation that happens after {@code now} does not count yet.
     */
    public static PunishmentStatus resolve(Instant expiresAt, Instant revokedAt, Instant now) {
        boolean revokedByNow = revokedAt != null && !revokedAt.isAfter(now);
        boolean revokedBeforeExpiry = revokedByNow && (expiresAt == null || revokedAt.isBefore(expiresAt));

        if (revokedBeforeExpiry) {
            return REVOKED;
        }

        boolean expired = expiresAt != null && !now.isBefore(expiresAt);

        if (expired) {
            return EXPIRED;
        }

        return ACTIVE;
    }
}
