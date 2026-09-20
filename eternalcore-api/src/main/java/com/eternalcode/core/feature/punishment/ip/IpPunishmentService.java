package com.eternalcode.core.feature.punishment.ip;

import com.eternalcode.core.feature.punishment.PunishmentTarget;

import net.kyori.adventure.text.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface IpPunishmentService {

    void banIp(String ip, PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt, List<Component> kickMessage);

    void unbanIp(String ip, PunishmentTarget target);

    boolean isIpBanned(String ip);

    Optional<IpPunishment> getActiveIpBan(String ip);
}
