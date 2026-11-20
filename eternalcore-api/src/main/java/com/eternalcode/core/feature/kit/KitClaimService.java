package com.eternalcode.core.feature.kit;

import org.bukkit.entity.Player;

public interface KitClaimService {

    /**
     * Full claim flow: permission check, cooldown check, {@link com.eternalcode.core.feature.kit.event.KitClaimEvent},
     * cooldown and item/command delivery. Must be called from the player's thread (main thread on Paper).
     */
    KitClaimResult claimKit(Player player, Kit kit);

}
