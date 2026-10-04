package com.eternalcode.core.feature.punishment.ban;

import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.feature.punishment.ActivePunishmentCache;
import com.eternalcode.core.feature.punishment.PlayerKicker;
import com.eternalcode.core.feature.punishment.PunishmentGuard;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.database.BanRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import net.kyori.adventure.text.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
class BanServiceImpl implements BanService {

    private static final String SERVICE_NAME = "BanService";

    private final ActivePunishmentCache<UUID, Ban> activeBans = new ActivePunishmentCache<>();

    private final BanRepository banRepository;
    private final PlayerKicker playerKicker;
    private final PunishmentGuard punishmentGuard;

    @Inject
    BanServiceImpl(BanRepository banRepository, PlayerKicker playerKicker, PunishmentGuard punishmentGuard) {
        this.banRepository = banRepository;
        this.playerKicker = playerKicker;
        this.punishmentGuard = punishmentGuard;

        this.loadActiveBans();
    }

    @Override
    public Ban ban(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt, List<Component> kickMessage) {
        this.punishmentGuard.assertAsync(SERVICE_NAME);
        this.punishmentGuard.requireKickMessage(kickMessage);

        Ban ban = Ban.issue(target, operator, reason, expiresAt);

        this.banRepository.save(ban).join();
        this.activeBans.put(target.uuid(), ban);
        this.playerKicker.kick(target.uuid(), kickMessage);

        return ban;
    }

    @Override
    public void unban(PunishmentTarget target, PunishmentTarget operator) {
        this.punishmentGuard.assertAsync(SERVICE_NAME);

        this.activeBans.remove(target.uuid())
            .ifPresent(ban -> this.banRepository.revoke(ban.id(), operator, Instant.now()).join());
    }

    @Override
    public boolean isBanned(UUID targetUuid) {
        return this.getActiveBan(targetUuid).isPresent();
    }

    @Override
    public Optional<Ban> getActiveBan(UUID targetUuid) {
        return this.activeBans.get(targetUuid);
    }

    @Override
    public List<Ban> activeBans() {
        return this.activeBans.active();
    }

    private void loadActiveBans() {
        this.banRepository.findAllActive()
            .thenAccept(bans -> bans.forEach(ban -> this.activeBans.put(ban.target().uuid(), ban)))
            .exceptionally(FutureHandler::handleException);
    }
}
