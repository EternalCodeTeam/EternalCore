package com.eternalcode.core.feature.punishment.ip;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryEntry;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryEntry.HistoryAction;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.ip.IpCryptoService;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
class IpPunishmentServiceImpl implements IpPunishmentService {

    private final Map<String, IpPunishment> activeByIpHash = new ConcurrentHashMap<>();

    private final IpPunishmentRepository ipPunishmentRepository;
    private final PunishmentHistoryService punishmentHistoryService;
    private final IpCryptoService ipCryptoService;
    private final Server server;
    private final Scheduler scheduler;

    @Inject
    IpPunishmentServiceImpl(
        IpPunishmentRepository ipPunishmentRepository,
        PunishmentHistoryService punishmentHistoryService,
        IpCryptoService ipCryptoService,
        Server server,
        Scheduler scheduler
    ) {
        this.ipPunishmentRepository = ipPunishmentRepository;
        this.punishmentHistoryService = punishmentHistoryService;
        this.ipCryptoService = ipCryptoService;
        this.server = server;
        this.scheduler = scheduler;

        this.loadActivePunishments();
    }

    @Override
    public void banIp(String ip, PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt, List<Component> kickMessage) {
        this.assertNotPrimaryThread();
        this.requireNonEmpty(kickMessage);

        IpPunishment ipPunishment = IpPunishment.builder()
            .ip(ip)
            .target(target)
            .operator(operator)
            .reason(reason)
            .expiresAt(expiresAt)
            .build();

        this.ipPunishmentRepository.save(ipPunishment).join();
        this.recordHistory(ipPunishment, HistoryAction.BAN_IP);

        this.activeByIpHash.put(this.ipCryptoService.hash(ip), ipPunishment);
        this.kickEveryoneOnIp(ip, kickMessage);
    }

    @Override
    public void unbanIp(String ip, PunishmentTarget operator) {
        this.assertNotPrimaryThread();

        String hash = this.ipCryptoService.hash(ip);
        IpPunishment cached = this.activeByIpHash.remove(hash);

        if (cached == null) {
            return;
        }

        PunishmentHistoryEntry entry = new PunishmentHistoryEntry(
            UUID.randomUUID(),
            cached.id(),
            cached.target(),
            operator,
            HistoryAction.UNBAN_IP,
            "",
            Instant.now(),
            null
        );

        this.ipPunishmentRepository.deactivate(cached.id()).join();
        this.punishmentHistoryService.record(entry);
    }

    @Override
    public boolean isIpBanned(String ip) {
        return this.getActiveIpBan(ip).isPresent();
    }

    @Override
    public Optional<IpPunishment> getActiveIpBan(String ip) {
        String hash = this.ipCryptoService.hash(ip);
        IpPunishment punishment = this.activeByIpHash.get(hash);

        if (punishment == null) {
            return Optional.empty();
        }

        if (!punishment.isActive()) {
            this.activeByIpHash.remove(hash);
            return Optional.empty();
        }

        return Optional.of(punishment);
    }

    private void recordHistory(IpPunishment punishment, HistoryAction action) {
        PunishmentHistoryEntry entry = new PunishmentHistoryEntry(
            UUID.randomUUID(),
            punishment.id(),
            punishment.target(),
            punishment.operator(),
            action,
            punishment.reason(),
            punishment.createdAt(),
            punishment.expiresAt()
        );

        this.punishmentHistoryService.record(entry);
    }

    private void kickEveryoneOnIp(String ip, List<Component> message) {
        this.scheduler.run(() -> {
            Component joined = Component.join(JoinConfiguration.newlines(), message);

            for (Player player : this.server.getOnlinePlayers()) {
                if (player.getAddress() != null && ip.equals(player.getAddress().getAddress().getHostAddress())) {
                    player.kick(joined);
                }
            }
        });
    }

    private void requireNonEmpty(List<Component> kickMessage) {
        Objects.requireNonNull(kickMessage, "kickMessage cannot be null");

        if (kickMessage.isEmpty()) {
            throw new IllegalArgumentException("kickMessage cannot be empty");
        }
    }

    private void assertNotPrimaryThread() {
        if (this.server.isPrimaryThread()) {
            throw new IllegalStateException("IpPunishmentService must not be called from the main thread");
        }
    }

    private void loadActivePunishments() {
        this.ipPunishmentRepository.findAllActive().thenAccept(punishments -> {
            for (IpPunishment punishment : punishments) {
                if (punishment.isActive()) {
                    this.activeByIpHash.put(this.ipCryptoService.hash(punishment.ip()), punishment);
                }
            }
        });
    }
}
