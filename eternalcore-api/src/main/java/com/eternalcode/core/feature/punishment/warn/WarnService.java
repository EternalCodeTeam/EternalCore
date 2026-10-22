package com.eternalcode.core.feature.punishment.warn;

import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentTarget;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface WarnService {

    /**
     * Blocking, must not be called from the main thread.
     * Applies configured escalations (kick / mute / ban) when the warn count is reached.
     */
    Warn warn(PunishmentTarget target, PunishmentActor operator, String reason, Instant expiresAt);

    /**
     * Blocking, must not be called from the main thread.
     *
     * @return true when the warn was active and got revoked, false when it was unknown, already expired or revoked
     */
    boolean revokeWarn(UUID warnId, PunishmentActor operator);

    List<Warn> activeWarns();
}
