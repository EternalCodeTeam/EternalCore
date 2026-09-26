package com.eternalcode.core.feature.kit.cooldown;

import com.eternalcode.commons.concurrent.FutureHandler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Server;

/**
 * Internal cooldown storage: in-memory cache of online players + write-through to the database.
 * The only class allowed to talk to {@link KitCooldownRepository}.
 */
@Service
public class KitCooldownStore {

    private final Map<UUID, Map<String, Instant>> cache = new ConcurrentHashMap<>();

    private final KitCooldownRepository repository;
    private final Server server;

    @Inject
    KitCooldownStore(KitCooldownRepository repository, Server server) {
        this.repository = repository;
        this.server = server;

        this.repository.deleteExpired(Instant.now())
            .exceptionally(FutureHandler::handleException);
    }

    public boolean isLoaded(UUID playerUniqueId) {
        return this.cache.containsKey(playerUniqueId);
    }

    Optional<Instant> findExpiration(UUID playerUniqueId, String kitName) {
        Map<String, Instant> expirations = this.cache.get(playerUniqueId);

        if (expirations == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(expirations.get(kitName));
    }

    Map<String, Instant> findExpirations(UUID playerUniqueId) {
        Map<String, Instant> expirations = this.cache.get(playerUniqueId);

        if (expirations == null) {
            return Map.of();
        }

        return Map.copyOf(expirations);
    }

    void save(UUID playerUniqueId, String kitName, Instant expiresAt) {
        Map<String, Instant> expirations = this.cache.get(playerUniqueId);

        if (expirations != null) {
            expirations.put(kitName, expiresAt);
        }

        this.repository.save(playerUniqueId, kitName, expiresAt)
            .exceptionally(FutureHandler::handleException);
    }

    void delete(UUID playerUniqueId, String kitName) {
        Map<String, Instant> expirations = this.cache.get(playerUniqueId);

        if (expirations != null) {
            expirations.remove(kitName);
        }

        this.repository.delete(playerUniqueId, kitName)
            .exceptionally(FutureHandler::handleException);
    }

    public void deleteKit(String kitName) {
        this.cache.values().forEach(expirations -> expirations.remove(kitName));

        this.repository.deleteByKit(kitName)
            .exceptionally(FutureHandler::handleException);
    }

    CompletableFuture<Void> load(UUID playerUniqueId) {
        return this.repository.findExpirations(playerUniqueId)
            .thenAccept(expirations -> {
                if (this.server.getPlayer(playerUniqueId) == null) {
                    return;
                }

                Instant now = Instant.now();
                Map<String, Instant> active = new ConcurrentHashMap<>();

                expirations.forEach((kitName, expiresAt) -> {
                    if (expiresAt.isAfter(now)) {
                        active.put(kitName, expiresAt);
                    }
                });

                this.cache.put(playerUniqueId, active);
            });
    }

    void unload(UUID playerUniqueId) {
        this.cache.remove(playerUniqueId);
    }
}
