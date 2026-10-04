package com.eternalcode.core.feature.punishment.notification;

import java.util.UUID;

/**
 * Per-player switch for punishment broadcasts about other players.
 * A player with hidden notifications still receives broadcasts about their own punishments.
 */
public interface PunishmentNotificationService {

    boolean isHidden(UUID playerUniqueId);

    /**
     * @throws IllegalArgumentException if the player is not online
     */
    void setHidden(UUID playerUniqueId, boolean hidden);
}
