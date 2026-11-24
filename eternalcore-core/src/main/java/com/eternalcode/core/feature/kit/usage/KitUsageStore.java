package com.eternalcode.core.feature.kit.usage;

import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.feature.kit.KitPlayerTaskQueue;
import com.eternalcode.core.feature.kit.KitUsage;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.bukkit.Server;

@Service
public class KitUsageStore {

    private final Map<UUID, Map<String, KitUsage>> cache = new ConcurrentHashMap<>();
    private final KitPlayerTaskQueue writeQueue = new KitPlayerTaskQueue();

    private final KitUsageRepository repository;
    private final Server server;

    @Inject
    KitUsageStore(KitUsageRepository repository, Server server) {
        this.repository = repository;
        this.server = server;
    }

    boolean isLoaded(UUID playerUniqueId) {
        return this.cache.containsKey(playerUniqueId);
    }

    KitUsage getUsage(UUID playerUniqueId, String kitName) {
        Map<String, KitUsage> usages = this.cache.get(playerUniqueId);

        if (usages == null) {
            return KitUsage.neverClaimed();
        }

        return usages.getOrDefault(kitName, KitUsage.neverClaimed());
    }

    Map<String, KitUsage> getUsages(UUID playerUniqueId) {
        Map<String, KitUsage> usages = this.cache.get(playerUniqueId);

        if (usages == null) {
            return Map.of();
        }

        return Map.copyOf(usages);
    }

    public void recordClaim(UUID playerUniqueId, String kitName, Instant claimedAt) {
        Map<String, KitUsage> usages = this.cache.get(playerUniqueId);

        if (usages == null) {
            throw new IllegalStateException("Kit usage of " + playerUniqueId + " is not loaded");
        }

        KitUsage updated = usages.getOrDefault(kitName, KitUsage.neverClaimed()).claimed(claimedAt);
        usages.put(kitName, updated);

        this.write(playerUniqueId, () -> this.repository.save(playerUniqueId, kitName, updated));
    }

    void resetClaim(UUID playerUniqueId, String kitName) {
        Map<String, KitUsage> usages = this.cache.get(playerUniqueId);

        if (usages != null) {
            usages.computeIfPresent(kitName, (name, usage) -> usage.reset());
        }

        this.write(playerUniqueId, () -> this.repository.resetClaim(playerUniqueId, kitName));
    }

    public void deleteKit(String kitName) {
        this.cache.values().forEach(usages -> usages.remove(kitName));

        this.repository.deleteByKit(kitName)
            .exceptionally(FutureHandler::handleException);
    }

    CompletableFuture<Void> load(UUID playerUniqueId) {
        return this.writeQueue.enqueue(playerUniqueId, () -> this.repository.findUsages(playerUniqueId))
            .thenAccept(usages -> {
                if (this.server.getPlayer(playerUniqueId) == null) {
                    return;
                }

                this.cache.put(playerUniqueId, new ConcurrentHashMap<>(usages));
            });
    }

    void unload(UUID playerUniqueId) {
        this.cache.remove(playerUniqueId);
        this.writeQueue.awaitPending(playerUniqueId, Duration.ofSeconds(3));
    }

    private void write(UUID playerUniqueId, Supplier<CompletableFuture<Void>> write) {
        this.writeQueue.enqueue(playerUniqueId, write)
            .exceptionally(FutureHandler::handleException);
    }
}
