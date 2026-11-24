package com.eternalcode.core.feature.kit;

import org.bukkit.entity.Player;

/**
 * All methods must be called from the player's thread (main thread on Paper, entity thread on Folia).
 */
public interface KitClaimService {

    /**
     * Checks every claim condition without claiming the kit or calling any event.
     * {@link KitClaimStatus#INVENTORY_FULL} is reported only when full-inventory behavior is set to cancel.
     */
    KitClaimResult canClaim(Player player, Kit kit);

    /**
     * Full claim flow: permission, type, cooldown and requirement checks, {@link com.eternalcode.core.feature.kit.event.PreKitClaimEvent},
     * persisting the claim, item and command delivery and {@link com.eternalcode.core.feature.kit.event.KitClaimEvent}.
     */
    KitClaimResult claimKit(Player player, Kit kit);

    /**
     * Gives the kit as an administrator: skips permission, cooldown and requirement checks and does not record a claim.
     */
    KitClaimResult giveKit(Player player, Kit kit);

}
