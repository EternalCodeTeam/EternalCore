package com.eternalcode.core.feature.punishment;

import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryEntry;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryEntry.HistoryAction;
import com.eternalcode.core.feature.punishment.history.PunishmentHistoryService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import java.util.Objects;
import java.util.Optional;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
class PunishmentServiceImpl implements PunishmentService {

    private final Map<UUID, Punishment> activeBans = new ConcurrentHashMap<>();
    private final Map<UUID, Punishment> activeMutes = new ConcurrentHashMap<>();

    private final PunishmentRepository punishmentRepository;
    private final PunishmentHistoryService punishmentHistoryService;
    private final Server server;
    private final Scheduler scheduler;

    @Inject
    PunishmentServiceImpl(
        PunishmentRepository punishmentRepository,
        PunishmentHistoryService punishmentHistoryService,
        Server server,
        Scheduler scheduler
    ) {
        this.punishmentRepository = punishmentRepository;
        this.punishmentHistoryService = punishmentHistoryService;
        this.server = server;
        this.scheduler = scheduler;

        this.loadActivePunishments();
    }

    @Override
    public CompletableFuture<Punishment> ban(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt, List<Component> kickMessage) {
        this.requireNonEmpty(kickMessage);

        Punishment punishment = Punishment.builder()
            .target(target)
            .operator(operator)
            .type(PunishmentType.BAN)
            .reason(reason)
            .expiresAt(expiresAt)
            .active(true)
            .build();

        return this.persistAndCache(punishment, this.activeBans, HistoryAction.BAN)
            .thenApply(saved -> {
                this.kickIfOnline(target.uuid(), kickMessage);
                return saved;
            });
    }

    @Override
    public CompletableFuture<Void> unban(PunishmentTarget target, PunishmentTarget operator) {
        return this.deactivateAndUncache(target, operator, this.activeBans, HistoryAction.UNBAN);
    }

    @Override
    public CompletableFuture<Void> kick(PunishmentTarget target, PunishmentTarget operator, String reason, List<Component> kickMessage) {
        this.requireNonEmpty(kickMessage);

        Punishment punishment = Punishment.builder()
            .target(target)
            .operator(operator)
            .type(PunishmentType.KICK)
            .reason(reason)
            .active(false)
            .build();

        this.kickIfOnline(target.uuid(), kickMessage);

        return this.punishmentRepository.save(punishment)
            .thenCompose(none -> this.recordHistory(punishment, HistoryAction.KICK));
    }

    @Override
    public CompletableFuture<Punishment> mute(PunishmentTarget target, PunishmentTarget operator, String reason, Instant expiresAt) {
        Punishment punishment = Punishment.builder()
            .target(target)
            .operator(operator)
            .type(PunishmentType.MUTE)
            .reason(reason)
            .expiresAt(expiresAt)
            .active(true)
            .build();

        return this.persistAndCache(punishment, this.activeMutes, HistoryAction.MUTE);
    }

    @Override
    public CompletableFuture<Void> unmute(PunishmentTarget target, PunishmentTarget operator) {
        return this.deactivateAndUncache(target, operator, this.activeMutes, HistoryAction.UNMUTE);
    }

    @Override
    public CompletableFuture<Punishment> warn(PunishmentTarget target, PunishmentTarget operator, String reason) {
        Punishment punishment = Punishment.builder()
            .target(target)
            .operator(operator)
            .type(PunishmentType.WARN)
            .reason(reason)
            .active(false)
            .build();

        return this.punishmentRepository.save(punishment)
            .thenCompose(none -> this.recordHistory(punishment, HistoryAction.WARN))
            .thenApply(none -> punishment);
    }

    @Override
    public boolean isBanned(UUID targetUuid) {
       return this.getActiveBan(targetUuid).isPresent();
    }

    @Override
    public boolean isMuted(UUID targetUuid) {
        return this.getActiveMute(targetUuid).isPresent();
    }

    @Override
    public Optional<Punishment> getActiveBan(UUID targetUuid) {
        return this.getActiveFromCache(this.activeBans, targetUuid);
    }

    @Override
    public Optional<Punishment> getActiveMute(UUID targetUuid) {
        return this.getActiveFromCache(this.activeMutes, targetUuid);
    }

    @Override
    public CompletableFuture<List<Punishment>> findActive(UUID targetUuid) {
        return this.punishmentRepository.findActive(targetUuid);
    }

    private Optional<Punishment> getActiveFromCache(Map<UUID, Punishment> cache, UUID targetUuid) {
        Punishment punishment = cache.get(targetUuid);

        if (punishment == null) {
            return Optional.empty();
        }

        if (this.isExpired(punishment)) {
            cache.remove(targetUuid);
            return Optional.empty();
        }

        return Optional.of(punishment);
    }

    private boolean isExpired(Punishment punishment) {
        return punishment.expiresAt()
            .map(expiresAt -> expiresAt.isBefore(Instant.now()))
            .orElse(false);
    }

    private CompletableFuture<Punishment> persistAndCache(Punishment punishment, Map<UUID, Punishment> cache, HistoryAction action) {
        return this.punishmentRepository.save(punishment)
            .thenCompose(none -> this.recordHistory(punishment, action))
            .thenApply(none -> {
                cache.put(punishment.target().uuid(), punishment);
                return punishment;
            });
    }

    private CompletableFuture<Void> deactivateAndUncache(PunishmentTarget target, PunishmentTarget operator, Map<UUID, Punishment> cache, HistoryAction action) {
        Punishment cached = cache.remove(target.uuid());

        if (cached == null) {
            return CompletableFuture.completedFuture(null);
        }

        PunishmentHistoryEntry entry = new PunishmentHistoryEntry(
            UUID.randomUUID(),
            cached.id(),
            target,
            operator,
            action,
            "",
            Instant.now()
        );

        return this.punishmentRepository.deactivate(cached.id())
            .thenCompose(none -> this.punishmentHistoryService.record(entry));
    }

    private CompletableFuture<Void> recordHistory(Punishment punishment, HistoryAction action) {
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

    private void kickIfOnline(UUID targetUuid, List<Component> message) {
        this.scheduler.run(() -> {
            Player player = this.server.getPlayer(targetUuid);

            if (player != null) {
                player.kick(Component.join(JoinConfiguration.newlines(), message));
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
        this.loadActiveInto(this.activeBans, PunishmentType.BAN);
        this.loadActiveInto(this.activeMutes, PunishmentType.MUTE);
    }

    private void loadActiveInto(Map<UUID, Punishment> cache, PunishmentType type) {
        this.punishmentRepository.findAllActive(type).thenAccept(punishments -> {
            for (Punishment punishment : punishments) {
                if (!this.isExpired(punishment)) {
                    cache.put(punishment.target().uuid(), punishment);
                }
            }
        });
    }
}
