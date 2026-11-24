package com.eternalcode.core.feature.kit;

import java.time.Duration;

/**
 * @param status        result of the claim.
 * @param remaining     remaining cooldown for {@link KitClaimStatus#ON_COOLDOWN}, missing playtime for
 *                      {@link KitClaimStatus#NOT_ENOUGH_PLAYTIME}, {@link Duration#ZERO} otherwise.
 * @param bufferedItems number of items that did not fit into the inventory and were moved to the player's kit buffer.
 */
public record KitClaimResult(KitClaimStatus status, Duration remaining, int bufferedItems) {

    public static KitClaimResult of(KitClaimStatus status) {
        return new KitClaimResult(status, Duration.ZERO, 0);
    }

    public static KitClaimResult success(int bufferedItems) {
        return new KitClaimResult(KitClaimStatus.SUCCESS, Duration.ZERO, bufferedItems);
    }

    public static KitClaimResult onCooldown(Duration remainingCooldown) {
        return new KitClaimResult(KitClaimStatus.ON_COOLDOWN, remainingCooldown, 0);
    }

    public static KitClaimResult notEnoughPlaytime(Duration missingPlaytime) {
        return new KitClaimResult(KitClaimStatus.NOT_ENOUGH_PLAYTIME, missingPlaytime, 0);
    }

    public boolean isSuccess() {
        return this.status == KitClaimStatus.SUCCESS;
    }
}
