package com.eternalcode.core.feature.punishment;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Immutable punishment record. Status is derived, never stored:
 * see {@link PunishmentStatus#resolve(Instant, Instant, Instant)}.
 * KICK is instantaneous and always has {@link PunishmentStatus#INSTANT} status.
 *
 * @param expiresAt null = permanent
 * @param revokedAt null = not revoked
 * @param revokedBy null = not revoked
 */
public record Punishment(
    UUID id,
    PunishmentTarget target,
    PunishmentTarget operator,
    PunishmentType type,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Instant revokedAt,
    PunishmentTarget revokedBy
) {

    public Punishment {
        if (expiresAt != null && expiresAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("expiresAt cannot be before createdAt");
        }
        if (revokedAt != null && revokedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("revokedAt cannot be before createdAt");
        }
        if ((revokedAt == null) != (revokedBy == null)) {
            throw new IllegalArgumentException("revokedAt and revokedBy must be both set or both null");
        }
    }

    public Optional<Instant> expiresAtOptional() {
        return Optional.ofNullable(this.expiresAt);
    }

    public Optional<Instant> revokedAtOptional() {
        return Optional.ofNullable(this.revokedAt);
    }

    public Optional<PunishmentTarget> revokedByOptional() {
        return Optional.ofNullable(this.revokedBy);
    }

    public boolean isPermanent() {
        return this.expiresAt == null;
    }

    public PunishmentStatus status(Instant now) {
        if (this.type == PunishmentType.KICK) {
            return PunishmentStatus.INSTANT;
        }

        return PunishmentStatus.resolve(this.expiresAt, this.revokedAt, now);
    }

    public PunishmentStatus status() {
        return this.status(Instant.now());
    }

    public boolean isActive(Instant now) {
        return this.status(now) == PunishmentStatus.ACTIVE;
    }

    public boolean isActive() {
        return this.isActive(Instant.now());
    }

    public Punishment revoke(PunishmentTarget revokedBy, Instant revokedAt) {
        if (!this.isActive(revokedAt)) {
            throw new IllegalStateException("Punishment " + this.id + " is not active (" + this.status(revokedAt) + ")");
        }

        return new Punishment(
            this.id,
            this.target,
            this.operator,
            this.type,
            this.reason,
            this.createdAt,
            this.expiresAt,
            revokedAt,
            revokedBy
        );
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private UUID id = UUID.randomUUID();
        private PunishmentTarget target;
        private PunishmentTarget operator;
        private PunishmentType type;
        private String reason;
        private Instant createdAt = Instant.now();
        private Instant expiresAt;
        private Instant revokedAt;
        private PunishmentTarget revokedBy;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder target(PunishmentTarget target) {
            this.target = target;
            return this;
        }

        public Builder operator(PunishmentTarget operator) {
            this.operator = operator;
            return this;
        }

        public Builder type(PunishmentType type) {
            this.type = type;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Builder revokedAt(Instant revokedAt) {
            this.revokedAt = revokedAt;
            return this;
        }

        public Builder revokedBy(PunishmentTarget revokedBy) {
            this.revokedBy = revokedBy;
            return this;
        }

        public Punishment build() {
            return new Punishment(
                this.id,
                this.target,
                this.operator,
                this.type,
                this.reason,
                this.createdAt,
                this.expiresAt,
                this.revokedAt,
                this.revokedBy
            );
        }
    }
}
