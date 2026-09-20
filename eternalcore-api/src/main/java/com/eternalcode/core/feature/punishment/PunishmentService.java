package com.eternalcode.core.feature.punishment;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.kyori.adventure.text.Component;

public interface PunishmentService {

    Punishment ban(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt, List<Component> kickMessage);
    void unban(PunishmentTarget target, PunishmentTarget operator);

    Punishment mute(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt);
    void unmute(PunishmentTarget target, PunishmentTarget operator);

    Punishment warn(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt);

    void kick(PunishmentTarget target, PunishmentTarget operator, String reason, List<Component> kickMessage, boolean massKick);

    boolean isBanned(UUID targetUuid);

    boolean isMuted(UUID targetUuid);

    Optional<Punishment> getActiveBan(UUID targetUuid);

    Optional<Punishment> getActiveMute(UUID targetUuid);

    List<Punishment> findActive(UUID targetUuid);

    List<Punishment> activeBans();
    List<Punishment> activeMutes();
    List<Punishment> activeWarns();
}
