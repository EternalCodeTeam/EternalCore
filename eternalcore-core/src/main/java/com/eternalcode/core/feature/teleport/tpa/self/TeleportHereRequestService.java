package com.eternalcode.core.feature.teleport.tpa.self;

import com.eternalcode.core.feature.teleport.tpa.TeleportRequestSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
class TeleportHereRequestService {

    private final Cache<UUID, UUID> requests;

    @Inject
    TeleportHereRequestService(TeleportRequestSettings settings) {
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
        Map<UUID, UUID> map = this.requests.asMap();

        for (Map.Entry<UUID, UUID> entry : map.entrySet()) {
            if (entry.getKey().equals(requester) && entry.getValue().equals(target)) {
                return true;
            }
        }

        return false;
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
