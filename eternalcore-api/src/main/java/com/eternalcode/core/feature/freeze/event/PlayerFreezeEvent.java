package com.eternalcode.core.feature.freeze.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.time.Duration;

/*
 * Event that is called when a player is frozen.
 */
public class PlayerFreezeEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;
    private final Duration duration;

    public PlayerFreezeEvent(Player player, Duration duration) {
        super(false);

        this.player = player;
        this.duration = duration;
    }

    /**
     * Gets the player that is frozen.
     *
     * @return The player that is frozen.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the duration of the freeze.
     *
     * @return The duration of the freeze.
     */
    public Duration getDuration() {
        return duration;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
