package com.eternalcode.core.feature.punishment.mute;

import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentTarget;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.Blocking;

public interface MuteService {

    /**
     * Blocking, must not be called from the main thread.
     */
    @Blocking
    Mute mute(PunishmentTarget target, PunishmentActor operator, String reason, Instant expiresAt);

    /**
     * Blocking, must not be called from the main thread.
     */
    @Blocking
    void unmute(PunishmentTarget target, PunishmentActor operator);

    boolean isMuted(UUID targetUuid);

    Mute getActiveMute(UUID targetUuid);

    List<Mute> activeMutes();
}
