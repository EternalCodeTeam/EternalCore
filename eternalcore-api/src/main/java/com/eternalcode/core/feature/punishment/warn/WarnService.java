package com.eternalcode.core.feature.punishment.warn;

import com.eternalcode.core.feature.punishment.PunishmentTarget;

import java.time.Instant;
import java.util.List;

public interface WarnService {

    /**
     * Blocking, must not be called from the main thread.
     * Applies configured escalations (kick / mute / ban) when the warn count is reached.
     */
    Warn warn(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt);

    List<Warn> activeWarns();
}
