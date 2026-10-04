package com.eternalcode.core.feature.punishment.mute;

import com.eternalcode.core.feature.punishment.PunishmentTarget;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MuteService {

    /**
     * Blocking, must not be called from the main thread.
     */
    Mute mute(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt);

    /**
     * Blocking, must not be called from the main thread.
     */
    void unmute(PunishmentTarget target, PunishmentTarget operator);

    boolean isMuted(UUID targetUuid);

    Optional<Mute> getActiveMute(UUID targetUuid);

    List<Mute> activeMutes();
}
