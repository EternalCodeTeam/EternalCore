package com.eternalcode.core.feature.punishment.ip;

import com.eternalcode.core.feature.punishment.PunishmentTarget;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class IpPunishment {

    private final UUID id;
    private final String ip;
    private final PunishmentTarget target;
    private final PunishmentTarget operator;
    private final String reason;
    private final Instant createdAt;
    private final Instant expiresAt;
    private final boolean active;

    private IpPunishment(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id cannot be null");
        this.ip = Objects.requireNonNull(builder.ip, "ip cannot be null");
        this.target = Objects.requireNonNull(builder.target, "target cannot be null");
        this.operator = Objects.requireNonNull(builder.operator, "operator cannot be null");
        this.reason = Objects.requireNonNull(builder.reason, "reason cannot be null");
        this.createdAt = Objects.requireNonNull(builder.createdAt, "createdAt cannot be null");
        this.expiresAt = builder.expiresAt;
        this.active = builder.active;

        if (this.ip.isBlank()) {
            throw new IllegalArgumentException("ip cannot be blank");
        }
        if (this.reason.isBlank()) {
            throw new IllegalArgumentException("reason cannot be blank");
        }
        if (this.expiresAt != null && this.expiresAt.isBefore(this.createdAt)) {
            throw new IllegalArgumentException("expiresAt cannot be before createdAt");
        }
    }

    public UUID id() {
        return this.id;
    }

    public String ip() {
        return this.ip;
    }

    public PunishmentTarget target() {
        return this.target;
    }

    public PunishmentTarget operator() {
        return this.operator;
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
        private String ip;
        private PunishmentTarget target;
        private PunishmentTarget operator;
        private String reason;
        private Instant createdAt = Instant.now();
        private Instant expiresAt;
        private boolean active = true;

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

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public IpPunishment build() {
            return new IpPunishment(this);
        }
    }
}
