package com.eternalcode.core.feature.punishment.banip;

import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.feature.punishment.ActivePunishmentCache;
import com.eternalcode.core.feature.punishment.PlayerKicker;
import com.eternalcode.core.feature.punishment.PunishmentGuard;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.ip.IpCryptoService;

import net.kyori.adventure.text.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

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
        PunishmentGuard punishmentGuard
    ) {
        this.banIpRepository = banIpRepository;
        this.ipCryptoService = ipCryptoService;
        this.playerKicker = playerKicker;
        this.punishmentGuard = punishmentGuard;

        this.loadActiveIpBans();
    }

    @Override
    public BanIp banIp(String ip, PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt, List<Component> kickMessage) {
        this.punishmentGuard.assertAsync(SERVICE_NAME);
        this.punishmentGuard.requireKickMessage(kickMessage);

        BanIp banIp = BanIp.issue(ip, target, operator, reason, expiresAt);

        this.banIpRepository.save(banIp).join();
        this.activeByIpHash.put(this.ipCryptoService.hash(ip), banIp);
        this.playerKicker.kickAllOnIp(ip, kickMessage);

        return banIp;
    }

    @Override
    public void unbanIp(String ip, PunishmentTarget operator) {
        this.punishmentGuard.assertAsync(SERVICE_NAME);

        this.activeByIpHash.remove(this.ipCryptoService.hash(ip))
            .ifPresent(ipBan -> this.banIpRepository.revoke(ipBan.id(), operator, Instant.now()).join());
    }

    @Override
    public boolean isIpBanned(String ip) {
        return this.getActiveIpBan(ip).isPresent();
    }

    @Override
    public Optional<BanIp> getActiveIpBan(String ip) {
        return this.activeByIpHash.get(this.ipCryptoService.hash(ip));
    }

    private void loadActiveIpBans() {
        this.banIpRepository.findAllActive()
            .thenAccept(ipBans -> ipBans.forEach(ipBan -> this.activeByIpHash.put(this.ipCryptoService.hash(ipBan.ip()), ipBan)))
            .exceptionally(FutureHandler::handleException);
    }
}
