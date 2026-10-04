package com.eternalcode.core.feature.punishment.ipban;

import com.eternalcode.core.feature.punishment.PunishmentTarget;

import net.kyori.adventure.text.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface IpBanService {

    /**
     * Blocking, must not be called from the main thread.
     */
    IpBan banIp(String ip, PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt, List<Component> kickMessage);

    /**
     * Blocking, must not be called from the main thread.
     */
    void unbanIp(String ip, PunishmentTarget operator);

    boolean isIpBanned(String ip);

    Optional<IpBan> getActiveIpBan(String ip);
}
