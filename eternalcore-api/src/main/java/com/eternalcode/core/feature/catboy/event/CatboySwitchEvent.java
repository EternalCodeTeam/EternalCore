package com.eternalcode.core.feature.catboy.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called when a player switches their catboy status.
 */
public class CatboySwitchEvent extends PlayerEvent {

    private static final HandlerList CATBOY_SWITCH_HANDLER_LIST = new HandlerList();

    private final boolean isCatboy;

    public CatboySwitchEvent(Player player, boolean isCatboy) {
        super(player);
        this.isCatboy = isCatboy;
    }

    public static HandlerList getHandlerList() {
        return CATBOY_SWITCH_HANDLER_LIST;
    }

    /**
     * Checks if the player is a catboy.
     */
    public boolean isCatboy() {
        return this.isCatboy;
    }

    @Override
    public HandlerList getHandlers() {
        return CATBOY_SWITCH_HANDLER_LIST;
    }
}
