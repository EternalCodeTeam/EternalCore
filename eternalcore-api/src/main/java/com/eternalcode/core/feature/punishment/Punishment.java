package com.eternalcode.core.feature.punishment;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Immutable punishmentapi record. Active state is derived, never stored:
 * a punishment is active when it is not revoked and not expired.
 * KICK is instantaneous and has no meaningful active state.
 *
 * @param expiresAt null = permanent
 * @param revokedAt null = not revoked
 */
public record Punishment(
    UUID id,
    PunishmentTarget target,
    PunishmentTarget operator,
    PunishmentType type,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Instant revokedAt
) {

    public Punishment {
        if (expiresAt != null && expiresAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("expiresAt cannot be before createdAt");
        }
        if (revokedAt != null && revokedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("revokedAt cannot be before createdAt");
        }
    }

    public Optional<Instant> expiresAtOptional() {
        return Optional.ofNullable(this.expiresAt);
    }

    public Optional<Instant> revokedAtOptional() {
        return Optional.ofNullable(this.revokedAt);
    }

    public boolean isPermanent() {
        return this.expiresAt == null;
    }

    public boolean isRevoked() {
        return this.revokedAt != null;
    }

    public boolean isExpired(Instant now) {
        return this.expiresAt != null && !now.isBefore(this.expiresAt);
    }

    public boolean isActive(Instant now) {
        return !this.isRevoked() && !this.isExpired(now);
    }

    public boolean isActive() {
        return this.isActive(Instant.now());
    }

    public Punishment revoke(Instant revokedAt) {
        if (this.isRevoked()) {
            throw new IllegalStateException("Punishment " + this.id + " is already revoked");
        }

        return new Punishment(
            this.id,
            this.target,
            this.operator,
            this.type,
            this.reason,
            this.createdAt,
            this.expiresAt,
            revokedAt
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

        public Punishment build() {
            return new Punishment(
                this.id,
                this.target,
                this.operator,
                this.type,
                this.reason,
                this.createdAt,
                this.expiresAt,
                this.revokedAt
            );
        }
    }
}
