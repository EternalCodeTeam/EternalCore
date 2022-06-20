package com.eternalcode.core.teleport.request;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TeleportRequestService {

    private final Cache<UUID, UUID> requests;

    public TeleportRequestService(TeleportRequestSettings settings) {
        this.requests = CacheBuilder
            .newBuilder()
            .expireAfterWrite(settings.teleportExpire())
            .build();
    }

    public void createRequest(UUID requester, UUID target) {
        this.requests.put(requester, target);
    }

    public void removeRequest(UUID requester) {
        this.requests.asMap().remove(requester);
    }

    public boolean hasRequest(UUID requester, UUID target) {
        Map<UUID, UUID> map = this.requests.asMap();

        for (Map.Entry<UUID, UUID> entry : map.entrySet()) {
            if (entry.getKey().equals(requester) && entry.getValue().equals(target)) {
                return true;
            }
        }

        return false;
    }

    public List<UUID> findRequests(UUID target) {
        Map<UUID, UUID> map = this.requests.asMap();

        List<UUID> requesters = new ArrayList<>();

        for (Map.Entry<UUID, UUID> entry : map.entrySet()) {
            if (entry.getValue().equals(target)) {
                requesters.add(entry.getKey());
            }
        }

        return requesters;
    }

}
