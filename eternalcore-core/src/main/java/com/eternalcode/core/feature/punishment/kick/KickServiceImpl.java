package com.eternalcode.core.feature.punishment.kick;

import com.eternalcode.core.feature.punishment.PlayerKicker;
import com.eternalcode.core.feature.punishment.PunishmentGuard;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import net.kyori.adventure.text.Component;

import java.util.List;

@Service
class KickServiceImpl implements KickService {

    private static final String SERVICE_NAME = "KickService";

    private final KickRepository kickRepository;
    private final PlayerKicker playerKicker;
    private final PunishmentGuard punishmentGuard;

    @Inject
    KickServiceImpl(KickRepository kickRepository, PlayerKicker playerKicker, PunishmentGuard punishmentGuard) {
        this.kickRepository = kickRepository;
        this.playerKicker = playerKicker;
        this.punishmentGuard = punishmentGuard;
    }

    @Override
    public Kick kick(PunishmentTarget target, PunishmentTarget operator, String reason, List<Component> kickMessage, boolean massKick) {
        this.punishmentGuard.assertAsync(SERVICE_NAME);
        this.punishmentGuard.requireKickMessage(kickMessage);

        Kick kick = Kick.issue(target, operator, reason, massKick);

        this.playerKicker.kick(target.uuid(), kickMessage);
        this.kickRepository.save(kick).join();

        return kick;
    }
}
