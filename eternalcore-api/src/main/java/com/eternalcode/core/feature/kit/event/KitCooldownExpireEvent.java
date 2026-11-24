package com.eternalcode.core.feature.kit.event;

import com.eternalcode.core.feature.kit.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called when the cooldown of a {@link com.eternalcode.core.feature.kit.KitType#RECURRING} kit expires
 * while the player is online. Cooldowns that expired while the player was offline do not trigger it.
 */
public class KitCooldownExpireEvent extends PlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Kit kit;

    public KitCooldownExpireEvent(Player player, Kit kit) {
        super(player);
        this.kit = kit;
    }

    public Kit getKit() {
        return this.kit;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
