package com.eternalcode.core.feature.kit;

import java.time.Duration;
import java.util.Objects;

/**
 * @param status            result of the claim.
 * @param remainingCooldown remaining cooldown, {@link Duration#ZERO} unless status is {@link KitClaimStatus#ON_COOLDOWN}.
 */
public record KitClaimResult(KitClaimStatus status, Duration remainingCooldown) {

    public static KitClaimResult of(KitClaimStatus status) {
        return new KitClaimResult(status, Duration.ZERO);
    }

    public static KitClaimResult onCooldown(Duration remainingCooldown) {
        return new KitClaimResult(KitClaimStatus.ON_COOLDOWN, remainingCooldown);
    }

    public boolean isSuccess() {
        return this.status == KitClaimStatus.SUCCESS;
    }
}
