package com.eternalcode.core.feature.kit;

import java.time.Instant;
import java.util.Optional;

/**
 * Persistent claim state of a single kit for a single player.
 *
 * @param lastClaimedAt moment of the last claim, {@code null} if never claimed or reset by an administrator.
 * @param claimCount    total number of claims, not affected by resets.
 */
public record KitUsage(Instant lastClaimedAt, int claimCount) {

    private static final KitUsage NEVER_CLAIMED = new KitUsage(null, 0);

    public static KitUsage neverClaimed() {
        return NEVER_CLAIMED;
    }

    public Optional<Instant> findLastClaimedAt() {
        return Optional.ofNullable(this.lastClaimedAt);
    }

    /**
     * @return {@code true} if the kit was claimed and the claim was not reset.
     */
    public boolean isClaimed() {
        return this.lastClaimedAt != null;
    }

    public KitUsage claimed(Instant claimedAt) {
        return new KitUsage(claimedAt, this.claimCount + 1);
    }

    public KitUsage reset() {
        return new KitUsage(null, this.claimCount);
    }
}
