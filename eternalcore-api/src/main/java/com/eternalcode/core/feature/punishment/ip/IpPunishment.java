package com.eternalcode.core.feature.punishment.ip;

import com.eternalcode.core.feature.punishment.PunishmentTarget;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Immutable IP punishment. Active state is derived, never stored:
 * a punishment is active when it is not revoked and not expired.
 *
 * @param expiresAt null = permanent
 * @param revokedAt null = not revoked
 */
public record IpPunishment(
    UUID id,
    String ip,
    PunishmentTarget target,
    PunishmentTarget operator,
    String reason,
    Instant createdAt,
    Instant expiresAt,
    Instant revokedAt
) {

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
        Objects.requireNonNull(now, "now cannot be null");

        return this.expiresAt != null && !now.isBefore(this.expiresAt);
    }

    public boolean isActive(Instant now) {
        return !this.isRevoked() && !this.isExpired(now);
    }

    public boolean isActive() {
        return this.isActive(Instant.now());
    }

    public IpPunishment revoke(Instant revokedAt) {
        if (this.isRevoked()) {
            throw new IllegalStateException("IP punishment " + this.id + " is already revoked");
        }

        return new IpPunishment(
            this.id,
            this.ip,
            this.target,
            this.operator,
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
        private String ip;
        private PunishmentTarget target;
        private PunishmentTarget operator;
        private String reason;
        private Instant createdAt = Instant.now();
        private Instant expiresAt;
        private Instant revokedAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder ip(String ip) {
            this.ip = ip;
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

        public IpPunishment build() {
            return new IpPunishment(
                this.id,
                this.ip,
                this.target,
                this.operator,
                this.reason,
                this.createdAt,
                this.expiresAt,
                this.revokedAt
            );
        }
    }
}
