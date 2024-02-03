package com.eternalcode.core.feature.catboy.event;

import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called when a player switches their catboy type.
 */
public class CatboyChangeTypeEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Cat.Type oldType;
    private Cat.Type newType;
    private boolean cancelled = false;

    public CatboyChangeTypeEvent(Player player, Cat.Type oldType, Cat.Type newType) {
        super(player);
        this.oldType = oldType;
        this.newType = newType;
    }

    /**
     * Gets the type of the catboy before the change.
     */
    public Cat.Type getOldType() {
        return this.oldType;
    }

    /**
     * Gets the new type of the catboy.
     */
    public Cat.Type getNewType() {
        return this.newType;
    }

    /**
     * Sets the new type of the catboy.
     */
    public void setNewType(Cat.Type newType) {
        this.newType = newType;
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
