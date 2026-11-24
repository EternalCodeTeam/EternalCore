package com.eternalcode.core.feature.kit.claim;

import com.eternalcode.core.injector.annotations.component.Service;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
class KitClaimLock {

    private final Set<UUID> claiming = ConcurrentHashMap.newKeySet();

    boolean tryAcquire(UUID playerUniqueId) {
        return this.claiming.add(playerUniqueId);
    }

    void release(UUID playerUniqueId) {
        this.claiming.remove(playerUniqueId);
    }
}
