package com.eternalcode.core.feature.kit.cooldown;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitCooldownService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class KitCooldownServiceImpl implements KitCooldownService {

    private final KitCooldownStore store;

    @Inject
    KitCooldownServiceImpl(KitCooldownStore store) {
        this.store = store;
    }

    @Override
    public Optional<Duration> getRemainingCooldown(UUID playerUniqueId, Kit kit) {
        return this.store.findExpiration(playerUniqueId, kit.name())
            .map(expiresAt -> Duration.between(Instant.now(), expiresAt))
            .filter(KitCooldownServiceImpl::isActive);
    }

    @Override
    public Map<String, Duration> getRemainingCooldowns(UUID playerUniqueId) {
        Instant now = Instant.now();

        return this.store.findExpirations(playerUniqueId).entrySet().stream()
            .map(entry -> Map.entry(entry.getKey(), Duration.between(now, entry.getValue())))
            .filter(entry -> isActive(entry.getValue()))
            .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void applyCooldown(UUID playerUniqueId, Kit kit) {
        if (!kit.hasCooldown()) {
            return;
        }

        this.store.save(playerUniqueId, kit.name(), Instant.now().plus(kit.cooldown()));
    }

    @Override
    public void resetCooldown(UUID playerUniqueId, Kit kit) {
        this.store.delete(playerUniqueId, kit.name());
    }

    private static boolean isActive(Duration remaining) {
        return !remaining.isNegative() && !remaining.isZero();
    }
}
