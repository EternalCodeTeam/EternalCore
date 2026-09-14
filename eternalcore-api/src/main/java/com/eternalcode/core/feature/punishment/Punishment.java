package com.eternalcode.core.feature.punishment;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Immutable punishment record. Use {@link Builder} to construct.
 * KICK is instantaneous (no expiresAt, active is meaningless -> false).
 */
public final class Punishment {

    private final UUID id;
    private final PunishmentTarget target;
    private final PunishmentTarget operator;
    private final PunishmentType type;
    private final String reason;
    private final Instant createdAt;
    private final Instant expiresAt; // null = permanent
    private final boolean active;

    private Punishment(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id cannot be null");
        this.target = Objects.requireNonNull(builder.target, "target cannot be null");
        this.operator = Objects.requireNonNull(builder.operator, "operator cannot be null");
        this.type = Objects.requireNonNull(builder.type, "type cannot be null");
        this.reason = Objects.requireNonNull(builder.reason, "reason cannot be null");
        this.createdAt = Objects.requireNonNull(builder.createdAt, "createdAt cannot be null");
        this.expiresAt = builder.expiresAt;
        this.active = builder.active;

        if (this.expiresAt != null && this.expiresAt.isBefore(this.createdAt)) {
            throw new IllegalArgumentException("expiresAt cannot be before createdAt");
        }
    }

    public UUID id() {
        return this.id;
    }

    public PunishmentTarget target() {
        return this.target;
    }

    public PunishmentTarget operator() {
        return this.operator;
    }

    public PunishmentType type() {
        return this.type;
    }

    public String reason() {
        return this.reason;
    }

    public Instant createdAt() {
        return this.createdAt;
    }

    public Optional<Instant> expiresAt() {
        return Optional.ofNullable(this.expiresAt);
    }

    public boolean isPermanent() {
        return this.expiresAt == null;
    }

    public boolean active() {
        return this.active;
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
        private boolean active = true;

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

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public Punishment build() {
            return new Punishment(this);
        }
    }
}
