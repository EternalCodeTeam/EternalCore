package com.eternalcode.core.feature.afk.event;

import com.eternalcode.core.feature.afk.Afk;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a player switches their afk status.
 */
public class AfkSwitchEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Afk afk;
    private final boolean isAfk;
    private boolean cancelled;

    public AfkSwitchEvent(Afk afk, boolean isAfk) {
        super(false);
        this.afk = afk;
        this.isAfk = isAfk;
    }

    public Afk getAfk() {
        return this.afk;
    }

    public boolean isAfk() {
        return this.isAfk;
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
