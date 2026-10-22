package com.eternalcode.core.feature.punishment;

import java.time.Instant;
import java.util.Optional;

/**
 * Punishment that lasts for a period of time and can be revoked early (ban, ip ban, mute, warn).
 * Status is derived, never stored: see {@link PunishmentStatus#resolve(Instant, Instant, Instant)}.
 */
public interface ExpirablePunishment extends Punishment {

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

    default PunishmentStatus status(Instant now) {
        Revocation revocation = this.revocation();
        Instant revokedAt = revocation == null ? null : revocation.revokedAt();

        return PunishmentStatus.resolve(this.expiresAt(), revokedAt, now);
    }

    default PunishmentStatus status() {
        return this.status(Instant.now());
    }

    default boolean isActive(Instant now) {
        return this.status(now) == PunishmentStatus.ACTIVE;
    }

    default boolean isActive() {
        return this.isActive(Instant.now());
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
