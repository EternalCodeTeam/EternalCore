package com.eternalcode.core.feature.kit;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Persistent per-player kit state: claims, cooldowns and one-time usage.
 * State of online players is kept in memory (loaded on join), every change is persisted to the database,
 * so it is shared across the whole network and survives restarts.
 */
public interface KitUsageService {

    /**
     * @return usage of the kit, {@link KitUsage#neverClaimed()} if the player never claimed it or is offline.
     */
    KitUsage getUsage(UUID playerUniqueId, Kit kit);

    /**
     * @return remaining cooldown of a {@link KitType#RECURRING} kit, empty if it can be claimed or the player is offline.
     */
    Optional<Duration> getRemainingCooldown(UUID playerUniqueId, Kit kit);

    /**
     * @return remaining cooldowns of all recurring kits that are still blocked, keyed by kit name (empty for offline players).
     */
    Map<String, Duration> getRemainingCooldowns(UUID playerUniqueId);

    /**
     * @return {@code true} once the player's state is loaded from the database after joining.
     */
    boolean isLoaded(UUID playerUniqueId);

    /**
     * Resets the cooldown of a recurring kit and the "already claimed" state of one-time kits.
     * Works for offline players too. The claim counter is kept.
     */
    void resetUsage(UUID playerUniqueId, Kit kit);

}
