package com.eternalcode.core.feature.punishment.banip;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.punishment.ActivePunishmentCache;
import com.eternalcode.core.feature.punishment.PlayerKicker;
import com.eternalcode.core.feature.punishment.PunishmentGuard;
import com.eternalcode.core.feature.punishment.PunishmentActor;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.ip.IpCryptoService;

import net.kyori.adventure.text.Component;

import java.time.Instant;
import java.util.List;

@Service
class BanIpServiceImpl implements BanIpService {

    private static final String SERVICE_NAME = "BanIpService";

    private final ActivePunishmentCache<String, BanIp> activeByIpHash = new ActivePunishmentCache<>();

    private final BanIpRepository banIpRepository;
    private final IpCryptoService ipCryptoService;
    private final PlayerKicker playerKicker;
    private final PunishmentGuard punishmentGuard;

    @Inject
    BanIpServiceImpl(
        BanIpRepository banIpRepository,
        IpCryptoService ipCryptoService,
        PlayerKicker playerKicker,
        PunishmentGuard punishmentGuard,
        Scheduler scheduler
    ) {
        this.banIpRepository = banIpRepository;
        this.ipCryptoService = ipCryptoService;
        this.playerKicker = playerKicker;
        this.punishmentGuard = punishmentGuard;

        scheduler.runAsync(this::loadActiveIpBans);
    }

    @Override
    public BanIp banIp(String ip, PunishmentTarget target, PunishmentActor operator, String reason, Instant expiresAt, List<Component> kickMessage) {
        this.punishmentGuard.assertAsync(SERVICE_NAME);
        this.punishmentGuard.requireKickMessage(kickMessage);

        BanIp banIp = BanIpImpl.issue(ip, target, operator, reason, expiresAt);

        this.banIpRepository.save(banIp);
        this.activeByIpHash.put(this.ipCryptoService.hash(ip), banIp);
        this.playerKicker.kickAllOnIp(ip, kickMessage);

        return banIp;
    }

    @Override
    public void unbanIp(String ip, PunishmentActor operator) {
        this.punishmentGuard.assertAsync(SERVICE_NAME);

        BanIp banIp = this.activeByIpHash.remove(this.ipCryptoService.hash(ip));

        if (banIp != null) {
            this.banIpRepository.revoke(banIp.id(), operator, Instant.now());
        }
    }

    @Override
    public boolean isIpBanned(String ip) {
        return this.getActiveIpBan(ip) != null;
    }

    @Override
    public BanIp getActiveIpBan(String ip) {
        return this.activeByIpHash.get(this.ipCryptoService.hash(ip));
    }

    private void loadActiveIpBans() {
        this.banIpRepository.findAllActive()
            .forEach(ipBan -> this.activeByIpHash.put(this.ipCryptoService.hash(ipBan.ip()), ipBan));
    }
}
