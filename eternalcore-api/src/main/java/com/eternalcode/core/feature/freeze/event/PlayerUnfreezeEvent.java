package com.eternalcode.core.feature.freeze.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/*
 * Event that is called when a player is unfrozen.
 */
public class PlayerUnfreezeEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;

    public PlayerUnfreezeEvent(Player player) {
        super(false);

        this.player = player;
    }

    /**
     * Gets the player that was unfrozen.
     *
     * @return The player that was unfrozen.
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
