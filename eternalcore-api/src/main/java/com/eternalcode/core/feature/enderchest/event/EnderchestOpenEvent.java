package com.eternalcode.core.feature.enderchest.event;

import java.util.UUID;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called right before a page of a paginated ender chest is shown to a viewer.
 */
public class EnderchestOpenEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID viewerUniqueId;
    private final UUID ownerUniqueId;
    private final int page;
    private boolean cancelled;

    public EnderchestOpenEvent(UUID viewerUniqueId, UUID ownerUniqueId, int page) {
        super(false);
        this.viewerUniqueId = viewerUniqueId;
        this.ownerUniqueId = ownerUniqueId;
        this.page = page;
    }

    public UUID getViewerUniqueId() {
        return this.viewerUniqueId;
    }

    public UUID getOwnerUniqueId() {
        return this.ownerUniqueId;
    }

    public int getPage() {
        return this.page;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
