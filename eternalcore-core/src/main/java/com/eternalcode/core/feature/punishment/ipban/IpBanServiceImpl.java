package com.eternalcode.core.feature.punishment.ipban;

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
class IpBanServiceImpl implements IpBanService {

    private static final String SERVICE_NAME = "IpBanService";

    private final ActivePunishmentCache<String, IpBan> activeByIpHash = new ActivePunishmentCache<>();

    private final IpBanRepository ipBanRepository;
    private final IpCryptoService ipCryptoService;
    private final PlayerKicker playerKicker;
    private final PunishmentGuard punishmentGuard;

    @Inject
    IpBanServiceImpl(
        IpBanRepository ipBanRepository,
        IpCryptoService ipCryptoService,
        PlayerKicker playerKicker,
        PunishmentGuard punishmentGuard
    ) {
        this.ipBanRepository = ipBanRepository;
        this.ipCryptoService = ipCryptoService;
        this.playerKicker = playerKicker;
        this.punishmentGuard = punishmentGuard;

        this.loadActiveIpBans();
    }

    @Override
    public IpBan banIp(String ip, PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt, List<Component> kickMessage) {
        this.punishmentGuard.assertAsync(SERVICE_NAME);
        this.punishmentGuard.requireKickMessage(kickMessage);

        IpBan ipBan = IpBan.issue(ip, target, operator, reason, expiresAt);

        this.ipBanRepository.save(ipBan).join();
        this.activeByIpHash.put(this.ipCryptoService.hash(ip), ipBan);
        this.playerKicker.kickAllOnIp(ip, kickMessage);

        return ipBan;
    }

    @Override
    public void unbanIp(String ip, PunishmentTarget operator) {
        this.punishmentGuard.assertAsync(SERVICE_NAME);

        this.activeByIpHash.remove(this.ipCryptoService.hash(ip))
            .ifPresent(ipBan -> this.ipBanRepository.revoke(ipBan.id(), operator, Instant.now()).join());
    }

    @Override
    public boolean isIpBanned(String ip) {
        return this.getActiveIpBan(ip).isPresent();
    }

    @Override
    public Optional<IpBan> getActiveIpBan(String ip) {
        return this.activeByIpHash.get(this.ipCryptoService.hash(ip));
    }

    private void loadActiveIpBans() {
        this.ipBanRepository.findAllActive()
            .thenAccept(ipBans -> ipBans.forEach(ipBan -> this.activeByIpHash.put(this.ipCryptoService.hash(ipBan.ip()), ipBan)))
            .exceptionally(FutureHandler::handleException);
    }
}
