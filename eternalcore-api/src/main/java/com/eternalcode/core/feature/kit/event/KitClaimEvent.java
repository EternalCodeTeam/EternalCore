package com.eternalcode.core.feature.kit.event;

import com.eternalcode.core.feature.kit.Kit;
import com.eternalcode.core.feature.kit.KitClaimSource;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called after a kit was given to the player and its commands were scheduled.
 */
public class KitClaimEvent extends PlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Kit kit;
    private final KitClaimSource source;
    private final int bufferedItems;

    public KitClaimEvent(Player player, Kit kit, KitClaimSource source, int bufferedItems) {
        super(player);
        this.kit = kit;
        this.source = source;
        this.bufferedItems = bufferedItems;
    }

    public Kit getKit() {
        return this.kit;
    }

    public KitClaimSource getSource() {
        return this.source;
    }

    /**
     * @return number of items that did not fit into the inventory and were moved to the player's kit buffer.
     */
    public int getBufferedItems() {
        return this.bufferedItems;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
