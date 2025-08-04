package com.eternalcode.core.feature.vanish.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.entity.Player;

public abstract class AbstractVanishEvent extends Event implements Cancellable {

    private final Player player;
    private boolean cancelled = false;

    public AbstractVanishEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

}
