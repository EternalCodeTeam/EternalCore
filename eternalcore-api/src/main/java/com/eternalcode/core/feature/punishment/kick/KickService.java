package com.eternalcode.core.feature.punishment.kick;

import com.eternalcode.core.feature.punishment.PunishmentTarget;

import net.kyori.adventure.text.Component;

import java.util.List;

public interface KickService {

    /**
     * Blocking, must not be called from the main thread.
     */
    Kick kick(PunishmentTarget target, PunishmentTarget operator, String reason, List<Component> kickMessage, boolean massKick);
}
