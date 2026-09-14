package com.eternalcode.core.feature.punishment;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.kyori.adventure.text.Component;

public interface PunishmentService {

    CompletableFuture<Punishment> ban(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt, List<Component> kickMessage);

    CompletableFuture<Void> unban(PunishmentTarget target, PunishmentTarget operator);

    CompletableFuture<Void> kick(PunishmentTarget target, PunishmentTarget operator, String reason, List<Component> kickMessage, boolean massKick);

    CompletableFuture<Punishment> mute(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt);

    CompletableFuture<Void> unmute(PunishmentTarget target, PunishmentTarget operator);

    CompletableFuture<Punishment> warn(PunishmentTarget target, PunishmentTarget operator, String reason);

    Optional<Punishment> getActiveBan(UUID targetUuid);

    Optional<Punishment> getActiveMute(UUID targetUuid);

    CompletableFuture<List<Punishment>> findActive(UUID targetUuid);

    boolean isBanned(UUID targetUuid);

    boolean isMuted(UUID targetUuid);
}
