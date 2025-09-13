package com.eternalcode.paper.phantom;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.jetbrains.annotations.NotNull;

/**
 * Internal event of {@link com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent}
 */
public class PhantomSpawnAttemptEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Entity entity;
    private final Location location;
    private final SpawnReason spawnReason;
    private boolean cancelled = false;

    public PhantomSpawnAttemptEvent(Entity entity, Location location, SpawnReason spawnReason) {
        this.entity = entity;
        this.location = location;
        this.spawnReason = spawnReason;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Location getLocation() {
        return this.location;
    }

    public SpawnReason getSpawnReason() {
        return this.spawnReason;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
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
