package com.eternalcode.core.feature.kit.event;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitClaimSource;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called right before a kit is given to the player, after all checks passed.
 * Cancelling it prevents the delivery and the claim is not recorded.
 */
public class PreKitClaimEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Kit kit;
    private final KitClaimSource source;
    private final boolean bypassingCooldown;

    private boolean cancelled;

    public PreKitClaimEvent(Player player, Kit kit, KitClaimSource source, boolean bypassingCooldown) {
        super(player);
        this.kit = kit;
        this.source = source;
        this.bypassingCooldown = bypassingCooldown;
    }

    public Kit getKit() {
        return this.kit;
    }

    public KitClaimSource getSource() {
        return this.source;
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
