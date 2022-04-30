package com.eternalcode.core.teleport;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import panda.std.Option;

import java.time.Duration;
import java.util.UUID;

public final class TeleportRequestManager {

    private final Cache<UUID, UUID> teleportRequests;

    public TeleportRequestManager() {
        this.teleportRequests = CacheBuilder
            .newBuilder()
            .expireAfterWrite(Duration.ofMinutes(1))
            .build();
    }

    public Option<UUID> findTeleportRequest(UUID uuid) {
        return Option.of(this.teleportRequests.getIfPresent(uuid));
    }

    public void addTeleportRequest(UUID uuid, UUID request) {
        this.teleportRequests.put(uuid, request);
    }

    public void removeTeleportRequest(UUID uuid) {
        this.teleportRequests.asMap().remove(uuid);
    }

    public boolean contains(UUID uuid) {
        return this.teleportRequests.asMap().containsKey(uuid);
    }
}
