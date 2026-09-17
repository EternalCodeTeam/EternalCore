package com.eternalcode.core.feature.punishment.history;

import com.eternalcode.core.feature.punishment.PunishmentTarget;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class PunishmentHistoryEntry {

    private final UUID id;
    private final UUID punishmentId;
    private final PunishmentTarget target;
    private final PunishmentTarget operator;
    private final HistoryAction action;
    private final String reason;
    private final Instant timestamp;
    private final Instant expiresAt;

    public PunishmentHistoryEntry(UUID id, UUID punishmentId, PunishmentTarget target,
        PunishmentTarget operator, HistoryAction action, String reason, Instant timestamp, Instant expiresAt) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.punishmentId = Objects.requireNonNull(punishmentId, "punishmentId cannot be null");
        this.target = Objects.requireNonNull(target, "target cannot be null");
        this.operator = Objects.requireNonNull(operator, "operator cannot be null");
        this.action = Objects.requireNonNull(action, "action cannot be null");
        this.reason = Objects.requireNonNull(reason, "reason cannot be null");
        this.timestamp = Objects.requireNonNull(timestamp, "timestamp cannot be null");
        this.expiresAt = expiresAt;
    }

    public UUID id() {
        return this.id;
    }

    public UUID punishmentId() {
        return this.punishmentId;
    }

    public PunishmentTarget target() {
        return this.target;
    }

    public PunishmentTarget operator() {
        return this.operator;
    }

    public HistoryAction action() {
        return this.action;
    }

    public String reason() {
        return this.reason;
    }

    public Instant timestamp() {
        return this.timestamp;
    }

    public Optional<Instant> expiresAt() {
        return Optional.ofNullable(this.expiresAt);
    }

    public boolean isPermanent() {
        return this.expiresAt == null;
    }

    public enum HistoryAction {
        BAN, UNBAN, KICK, KICK_ALL, MUTE, UNMUTE, WARN, EXPIRE, BAN_IP, UNBAN_IP
    }
}
