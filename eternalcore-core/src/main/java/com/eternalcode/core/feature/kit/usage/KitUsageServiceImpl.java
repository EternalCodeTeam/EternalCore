package com.eternalcode.core.feature.kit.usage;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitService;
import com.eternalcode.core.feature.kit.KitUsage;
import com.eternalcode.core.feature.kit.KitUsageService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
class KitUsageServiceImpl implements KitUsageService {

    private final KitUsageStore store;
    private final KitService kitService;

    @Inject
    KitUsageServiceImpl(KitUsageStore store, KitService kitService) {
        this.store = store;
        this.kitService = kitService;
    }

    @Override
    public KitUsage getUsage(UUID playerUniqueId, Kit kit) {
        return this.store.getUsage(playerUniqueId, kit.name());
    }

    @Override
    public Optional<Duration> getRemainingCooldown(UUID playerUniqueId, Kit kit) {
        return this.remainingCooldown(kit, this.getUsage(playerUniqueId, kit), Instant.now());
    }

    @Override
    public Map<String, Duration> getRemainingCooldowns(UUID playerUniqueId) {
        Instant now = Instant.now();
        Map<String, Duration> cooldowns = new HashMap<>();

        this.store.getUsages(playerUniqueId).forEach((kitName, usage) -> this.kitService.findKit(kitName)
            .flatMap(kit -> this.remainingCooldown(kit, usage, now))
            .ifPresent(remaining -> cooldowns.put(kitName, remaining)));

        return Map.copyOf(cooldowns);
    }

    @Override
    public boolean isLoaded(UUID playerUniqueId) {
        return this.store.isLoaded(playerUniqueId);
    }

    @Override
    public void resetUsage(UUID playerUniqueId, Kit kit) {
        this.store.resetClaim(playerUniqueId, kit.name());
    }

    private Optional<Duration> remainingCooldown(Kit kit, KitUsage usage, Instant now) {
        if (!kit.hasCooldown()) {
            return Optional.empty();
        }

        return usage.findLastClaimedAt()
            .map(lastClaimedAt -> Duration.between(now, lastClaimedAt.plus(kit.cooldown())))
            .filter(remaining -> !remaining.isNegative() && !remaining.isZero());
    }
}
