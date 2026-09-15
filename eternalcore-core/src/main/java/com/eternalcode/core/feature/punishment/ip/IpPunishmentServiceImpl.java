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
import java.util.concurrent.CompletableFuture;

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
        this.ipPunishmentRepository = Objects.requireNonNull(ipPunishmentRepository, "ipPunishmentRepository cannot be null");
        this.punishmentHistoryService = Objects.requireNonNull(punishmentHistoryService, "punishmentHistoryService cannot be null");
        this.ipCryptoService = Objects.requireNonNull(ipCryptoService, "ipCryptoService cannot be null");
        this.server = Objects.requireNonNull(server, "server cannot be null");
        this.scheduler = Objects.requireNonNull(scheduler, "scheduler cannot be null");

        this.loadActivePunishments();
    }

    @Override
    public CompletableFuture<Void> banIp(String ip, PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt, List<Component> kickMessage) {
        Objects.requireNonNull(ip, "ip cannot be null");
        this.requireNonEmpty(kickMessage);

        IpPunishment ipPunishment = IpPunishment.builder()
            .ip(ip)
            .target(target)
            .operator(operator)
            .reason(reason)
            .expiresAt(expiresAt)
            .active(true)
            .build();

        return this.ipPunishmentRepository.save(ipPunishment)
            .thenCompose(none -> this.recordHistory(ipPunishment, HistoryAction.BAN_IP))
            .thenApply(none -> {
                this.activeByIpHash.put(this.ipCryptoService.hash(ip), ipPunishment);
                this.kickEveryoneOnIp(ip, kickMessage);
                return null;
            });
    }

    @Override
    public CompletableFuture<Void> unbanIp(String ip, PunishmentTarget operator) {
        Objects.requireNonNull(ip, "ip cannot be null");

        String hash = this.ipCryptoService.hash(ip);
        IpPunishment cached = this.activeByIpHash.remove(hash);

        if (cached == null) {
            return CompletableFuture.completedFuture(null);
        }

        PunishmentHistoryEntry entry = new PunishmentHistoryEntry(
            UUID.randomUUID(),
            cached.id(),
            cached.target(),
            operator,
            HistoryAction.UNBAN_IP,
            "",
            Instant.now()
        );

        return this.ipPunishmentRepository.deactivate(cached.id())
            .thenCompose(none -> this.punishmentHistoryService.record(entry));
    }

    @Override
    public boolean isIpBanned(String ip) {
        return this.getActiveIpBan(ip).isPresent();
    }

    @Override
    public Optional<IpPunishment> getActiveIpBan(String ip) {
        Objects.requireNonNull(ip, "ip cannot be null");

        String hash = this.ipCryptoService.hash(ip);
        IpPunishment punishment = this.activeByIpHash.get(hash);

        if (punishment == null) {
            return Optional.empty();
        }

        if (this.isExpired(punishment)) {
            this.activeByIpHash.remove(hash);
            return Optional.empty();
        }

        return Optional.of(punishment);
    }

    private CompletableFuture<Void> recordHistory(IpPunishment punishment, HistoryAction action) {
        PunishmentHistoryEntry entry = new PunishmentHistoryEntry(
            UUID.randomUUID(),
            punishment.id(),
            punishment.target(),
            punishment.operator(),
            action,
            punishment.reason(),
            punishment.createdAt()
        );

        return this.punishmentHistoryService.record(entry);
    }

    private boolean isExpired(IpPunishment punishment) {
        return punishment.expiresAt()
            .map(expiresAt -> expiresAt.isBefore(Instant.now()))
            .orElse(false);
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

    private void loadActivePunishments() {
        this.ipPunishmentRepository.findAllActive().thenAccept(punishments -> {
            for (IpPunishment punishment : punishments) {
                if (!this.isExpired(punishment)) {
                    this.activeByIpHash.put(this.ipCryptoService.hash(punishment.ip()), punishment);
                }
            }
        });
    }
}
