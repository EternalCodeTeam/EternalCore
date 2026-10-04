package com.eternalcode.core.feature.punishment.ban;

import com.eternalcode.core.feature.punishment.PunishmentTarget;

import net.kyori.adventure.text.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BanService {

    /**
     * Blocking, must not be called from the main thread.
     */
    Ban ban(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt, List<Component> kickMessage);

    /**
     * Blocking, must not be called from the main thread.
     */
    void unban(PunishmentTarget target, PunishmentTarget operator);

    boolean isBanned(UUID targetUuid);

    Optional<Ban> getActiveBan(UUID targetUuid);

    List<Ban> activeBans();
}
