package com.eternalcode.core.feature.punishment.banip;

import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentTarget;

import net.kyori.adventure.text.Component;

import java.time.Instant;
import java.util.List;
import org.jetbrains.annotations.Blocking;

public interface BanIpService {

    /**
     * Blocking, must not be called from the main thread.
     */
    @Blocking
    BanIp banIp(String ip, PunishmentTarget target, PunishmentActor operator, String reason, Instant expiresAt, List<Component> kickMessage);

    /**
     * Blocking, must not be called from the main thread.
     */
    @Blocking
    void unbanIp(String ip, PunishmentActor operator);

    boolean isIpBanned(String ip);

    BanIp getActiveIpBan(String ip);
}
