package com.eternalcode.core.feature.kit;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Kit cooldowns. Cooldowns of online players are kept in memory (loaded on join),
 * every change is persisted to the database, so cooldowns are shared across the whole network.
 */
public interface KitCooldownService {

    /**
     * @return remaining cooldown, or empty if the kit can be claimed or the player is offline.
     */
    Optional<Duration> getRemainingCooldown(UUID playerUniqueId, Kit kit);

    /**
     * @return remaining cooldowns of all kits that are still blocked, keyed by kit name (empty for offline players).
     */
    Map<String, Duration> getRemainingCooldowns(UUID playerUniqueId);

    /**
     * Starts the kit cooldown for the player. Kits without cooldown are ignored.
     */
    void applyCooldown(UUID playerUniqueId, Kit kit);

    /**
     * Resets the kit cooldown, works for offline players too.
     */
    void resetCooldown(UUID playerUniqueId, Kit kit);

}
