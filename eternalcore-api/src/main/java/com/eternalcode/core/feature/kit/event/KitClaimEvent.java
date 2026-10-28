package com.eternalcode.core.feature.kit.event;

import com.eternalcode.core.feature.kit.Kit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called right before a kit is given to the player (after permission and cooldown checks).
 * Cancelling it prevents both the delivery and the cooldown.
 */
public class KitClaimEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Kit kit;
    private final boolean bypassingCooldown;

    private boolean cancelled;

    public KitClaimEvent(Player player, Kit kit, boolean bypassingCooldown) {
        super(player);
        this.kit = kit;
        this.bypassingCooldown = bypassingCooldown;
    }

    public Kit getKit() {
        return this.kit;
    }

    public boolean isBypassingCooldown() {
        return this.bypassingCooldown;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
