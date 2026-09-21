package com.eternalcode.core.feature.enderchest;

import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.core.feature.enderchest.database.EnderchestRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@Service
class EnderchestManager {

    private static final Duration SHUTDOWN_WRITE_TIMEOUT = Duration.ofSeconds(10);
    private static final int MIN_PAGES = 1;
    private static final Predicate<Enderchest> UNVIEWED = Predicate.not(Enderchest::hasViewers);

    private final Map<UUID, CompletableFuture<Enderchest>> enderchests = new ConcurrentHashMap<>();
    private final Set<UUID> reportedFailures = ConcurrentHashMap.newKeySet();
    private final EnderchestRepository repository;
    private final EnderchestSettings settings;
    private final MinecraftScheduler scheduler;
    private final Server server;
    private final Logger logger;

    @Inject
    EnderchestManager(
        EnderchestRepository repository,
        EnderchestSettings settings,
        MinecraftScheduler scheduler,
        Server server,
        Logger logger
    ) {
        this.repository = repository;
        this.settings = settings;
        this.scheduler = scheduler;
        this.server = server;
        this.logger = logger;
    }

    CompletableFuture<Enderchest> loadEnderchest(UUID ownerUniqueId, @Nullable String ownerName) {
        CompletableFuture<Enderchest> future = this.enderchests.computeIfAbsent(ownerUniqueId, uniqueId -> {
            EnderchestLayout layout = EnderchestLayout.ofRows(this.settings.pages().rows());
            String name = ownerName != null ? ownerName : this.cachedOwnerName(uniqueId);

            return this.repository.findPages(uniqueId).thenApply(pages -> Enderchest.fromPages(uniqueId, name, layout, pages));
        });

        Enderchest cachedEnderchest = loadedEnderchest(future);
        if (cachedEnderchest != null && ownerName != null) {
            cachedEnderchest.setOwnerName(ownerName);
        }

        future.whenComplete((enderchest, throwable) -> {
            if (throwable != null) {
                this.scheduler.run(() -> this.enderchests.remove(ownerUniqueId, future));
            }
        });

        return future;
    }

    boolean isLoaded(UUID ownerUniqueId, Enderchest enderchest) {
        return loadedEnderchest(this.enderchests.get(ownerUniqueId)) == enderchest;
    }

    void saveEnderchest(Enderchest enderchest) {
        EnderchestWrite write = enderchest.prepareWrite();
        if (write.isEmpty()) {
            return;
        }

        this.repository.savePages(enderchest.getOwnerUniqueId(), write)
            .whenComplete((unused, throwable) -> this.scheduler.run(() -> {
                this.applyWriteResult(enderchest, write, throwable);
                enderchest.finishWrite();

                if (enderchest.hasNothingToWrite()) {
                    this.unloadIdleEnderchest(enderchest.getOwnerUniqueId());
                }
            }));
    }

    private void applyWriteResult(Enderchest enderchest, EnderchestWrite write, Throwable throwable) {
        if (throwable == null) {
            this.reportedFailures.remove(enderchest.getOwnerUniqueId());
            return;
        }

        enderchest.restoreDirtyPages(write);
        this.reportFailure(enderchest, throwable);
    }

    int getPageLimit(Player player) {
        int limit = this.settings.pages().limits().entrySet().stream()
            .filter(entry -> player.hasPermission(entry.getKey()))
            .mapToInt(Map.Entry::getValue)
            .max()
            .orElse(this.settings.pages().defaultLimit());

        return Math.max(MIN_PAGES, limit);
    }

    int getAccessiblePages(Enderchest enderchest) {
        Player owner = this.server.getPlayer(enderchest.getOwnerUniqueId());
        int limit = owner == null ? this.getHighestConfiguredLimit() : this.getPageLimit(owner);

        return Math.max(limit, enderchest.getUsedPages());
    }

    void unloadIdleEnderchest(UUID ownerUniqueId) {
        this.unloadEnderchestIf(ownerUniqueId, UNVIEWED.and(enderchest -> this.server.getPlayer(ownerUniqueId) == null));
    }

    void unloadUnviewedEnderchest(UUID ownerUniqueId) {
        this.unloadEnderchestIf(ownerUniqueId, UNVIEWED);
    }

    void unloadEnderchestOnQuit(UUID ownerUniqueId) {
        CompletableFuture<Enderchest> loadingEnderchest = this.enderchests.get(ownerUniqueId);

        if (loadingEnderchest != null && !loadingEnderchest.isDone()) {
            loadingEnderchest.whenComplete((enderchest, throwable) -> this.scheduler.run(() -> this.unloadIdleEnderchest(ownerUniqueId)));
            return;
        }

        this.unloadUnviewedEnderchest(ownerUniqueId);
    }

    void unloadAllEnderchests() {
        for (UUID ownerUniqueId : List.copyOf(this.enderchests.keySet())) {
            this.unloadUnviewedEnderchest(ownerUniqueId);
        }
    }

    void shutdown() {
        for (CompletableFuture<Enderchest> future : this.enderchests.values()) {
            Enderchest enderchest = loadedEnderchest(future);
            if (enderchest == null) {
                continue;
            }

            EnderchestWrite write = enderchest.prepareWrite();
            if (write.isEmpty()) {
                continue;
            }

            this.repository.savePages(enderchest.getOwnerUniqueId(), write)
                .whenComplete((unused, throwable) -> {
                    enderchest.finishWrite();

                    if (throwable != null) {
                        this.logger.log(Level.SEVERE, "Failed to save ender chest of " + enderchest.getOwnerName()
                            + " during shutdown, its changes are lost", throwable);
                    }
                });
        }

        this.repository.shutdownWrites(SHUTDOWN_WRITE_TIMEOUT);
    }

    private void unloadEnderchestIf(UUID ownerUniqueId, Predicate<Enderchest> idle) {
        CompletableFuture<Enderchest> future = this.enderchests.get(ownerUniqueId);
        Enderchest enderchest = loadedEnderchest(future);

        if (enderchest == null || !idle.test(enderchest)) {
            return;
        }

        this.saveEnderchest(enderchest);

        if (enderchest.isPersisted()) {
            this.enderchests.remove(ownerUniqueId, future);
            this.reportedFailures.remove(ownerUniqueId);
        }
    }

    private void reportFailure(Enderchest enderchest, Throwable throwable) {
        if (!this.reportedFailures.add(enderchest.getOwnerUniqueId())) {
            return;
        }

        this.logger.log(Level.SEVERE, "Failed to save ender chest of " + enderchest.getOwnerName()
            + ", it will be retried on every close and reported again after the next successful write", throwable);
    }

    private int getHighestConfiguredLimit() {
        int configuredLimit = this.settings.pages().limits().values().stream()
            .mapToInt(Integer::intValue)
            .max()
            .orElse(MIN_PAGES);

        return Math.max(MIN_PAGES, Math.max(configuredLimit, this.settings.pages().defaultLimit()));
    }

    private String cachedOwnerName(UUID ownerUniqueId) {
        String name = this.server.getOfflinePlayer(ownerUniqueId).getName();
        return name != null ? name : ownerUniqueId.toString();
    }

    @Nullable
    private static Enderchest loadedEnderchest(@Nullable CompletableFuture<Enderchest> future) {
        if (future == null || !future.isDone() || future.isCompletedExceptionally()) {
            return null;
        }

        return future.join();
    }
}
