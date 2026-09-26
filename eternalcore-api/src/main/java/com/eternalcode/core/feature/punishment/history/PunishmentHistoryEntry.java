package com.eternalcode.core.feature.punishment.history;

import com.eternalcode.core.feature.punishment.PunishmentTarget;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public record PunishmentHistoryEntry(
    UUID id,
    UUID punishmentId,
    PunishmentTarget target,
    PunishmentTarget operator,
    HistoryAction action,
    String reason,
    Instant timestamp,
    Instant expiresAt
) {
    public Optional<Instant> expiresAtOptional() {
        return Optional.ofNullable(this.expiresAt);
    }

    public boolean isPermanent() {
        return this.expiresAt == null;
    }

    public enum HistoryAction {
        BAN, UNBAN, KICK, KICK_ALL, MUTE, UNMUTE, WARN, BAN_IP, UNBAN_IP
    }
}
