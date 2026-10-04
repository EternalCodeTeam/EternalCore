package com.eternalcode.core.feature.punishment;

import java.time.Instant;

/**
 * Describes who and when revoked a punishment (unban, unmute, ...).
 */
public record Revocation(PunishmentTarget revokedBy, Instant revokedAt) {
}
