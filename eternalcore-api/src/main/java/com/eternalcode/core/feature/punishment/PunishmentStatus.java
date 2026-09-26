package com.eternalcode.core.feature.punishment;

import java.time.Instant;

public enum PunishmentStatus {

    ACTIVE,
    EXPIRED,
    REVOKED,
    INSTANT;

    public static PunishmentStatus resolve(Instant expiresAt, Instant revokedAt, Instant now) {
        boolean revokedBeforeExpiry = revokedAt != null && (expiresAt == null || revokedAt.isBefore(expiresAt));

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
