package com.eternalcode.core.feature.teleportrequest;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
class TeleportRequestService {

    private final Cache<UUID, UUID> requests;

    @Inject
    TeleportRequestService(TeleportRequestSettings settings) {
        this.requests = CacheBuilder
            .newBuilder()
            .expireAfterWrite(settings.tpaRequestExpire())
            .build();
    }

    void createRequest(UUID requester, UUID target) {
        this.requests.put(requester, target);
    }

    void removeRequest(UUID requester) {
        this.requests.asMap().remove(requester);
    }

    boolean hasRequest(UUID requester, UUID target) {
        UUID foundTarget = this.requests.getIfPresent(requester);
        return foundTarget != null && foundTarget.equals(target);
    }

    List<UUID> findRequests(UUID target) {
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
