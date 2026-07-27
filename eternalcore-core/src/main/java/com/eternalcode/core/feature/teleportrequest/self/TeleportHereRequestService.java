package com.eternalcode.core.feature.teleportrequest.self;

import com.eternalcode.core.feature.teleportrequest.TeleportRequestSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
class TeleportHereRequestService {

    private final Cache<UUID, Request> requests;

    @Inject
    TeleportHereRequestService(TeleportRequestSettings settings) {
        this.requests = CacheBuilder
            .newBuilder()
            .expireAfterWrite(settings.tpaRequestExpire())
            .build();
    }

    void createRequest(UUID requester, UUID target) {
        this.requests.put(requester, new Request(target, Instant.now()));
    }

    void removeRequest(UUID requester) {
        this.requests.asMap().remove(requester);
    }

    boolean hasRequest(UUID requester, UUID target) {
        Request request = this.requests.getIfPresent(requester);
        return request != null && request.target().equals(target);
    }

    List<UUID> findRequests(UUID target) {
        return this.requests.asMap().entrySet().stream()
            .filter(entry -> entry.getValue().target().equals(target))
            .sorted(Map.Entry.comparingByValue(Comparator.comparing(Request::createdAt).reversed()))
            .map(Map.Entry::getKey)
            .toList();
    }

    private record Request(UUID target, Instant createdAt) {}

}
