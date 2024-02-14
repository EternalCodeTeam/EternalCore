package com.eternalcode.core.feature.afk.event;

import com.eternalcode.core.feature.afk.Afk;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a player switches their afk status.
 */
public class AfkSwitchEvent extends Event implements Cancellable {

    private static final HandlerList AFK_HANDLER_LIST = new HandlerList();

    private final Afk afk;
    private boolean cancelled;

    public AfkSwitchEvent(Afk afk) {
        super(false);
        this.afk = afk;
    }

    public static HandlerList getAfkHandlerList() {
        return AFK_HANDLER_LIST;
    }

    public Afk getAfk() {
        return this.afk;
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
        return AFK_HANDLER_LIST;
    }
}
