package com.eternalcode.core.feature.punishment;

import java.time.Instant;
import java.util.Optional;

/**
 * Punishment that lasts for a period of time and can be revoked early (ban, ip ban, mute, warn).
 * Status is derived, never stored: see {@link RevocablePunishment#status()}.
 */
public interface RevocablePunishment extends Punishment {

    /**
     * @return moment when punishment expires, null = permanent
     */
    Instant expiresAt();

    /**
     * @return revocation details, null = not revoked
     */
    Revocation revocation();

    default Optional<Instant> expiresAtOptional() {
        return Optional.ofNullable(this.expiresAt());
    }

    default Optional<Revocation> revocationOptional() {
        return Optional.ofNullable(this.revocation());
    }

    default boolean isPermanent() {
        return this.expiresAt() == null;
    }

    @Override
    default PunishmentStatus status() {
        Instant now = Instant.now();
        Revocation revocation = this.revocation();
        Instant revokedAt = revocation == null ? null : revocation.revokedAt();

        Instant expiresAt = this.expiresAt();
        boolean revokedByNow = revokedAt != null && !revokedAt.isAfter(now);
        boolean revokedBeforeExpiry = revokedByNow && (expiresAt == null || revokedAt.isBefore(expiresAt));

        if (revokedBeforeExpiry) {
            return PunishmentStatus.REVOKED;
        }

        boolean expired = expiresAt != null && !now.isBefore(expiresAt);

        if (expired) {
            return PunishmentStatus.EXPIRED;
        }

        return PunishmentStatus.ACTIVE;
    }

    default boolean isActive() {
        return this.status() == PunishmentStatus.ACTIVE;
    }

    /**
     * Fail-fast validation shared by all expirable punishments.
     */
    static void validate(Instant createdAt, Instant expiresAt, Revocation revocation) {
        if (expiresAt != null && expiresAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("expiresAt cannot be before createdAt");
        }

        if (revocation != null && revocation.revokedAt().isBefore(createdAt)) {
            throw new IllegalArgumentException("revokedAt cannot be before createdAt");
        }
    }
}
