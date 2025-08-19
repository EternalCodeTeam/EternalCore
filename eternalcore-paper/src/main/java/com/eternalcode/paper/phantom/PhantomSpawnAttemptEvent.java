package com.eternalcode.paper.phantom;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.jetbrains.annotations.NotNull;

public class PhantomSpawnAttemptEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Entity player;
    private final Location spawnLocation;
    private final SpawnReason spawnReason;
    private boolean cancelled = false;

    public PhantomSpawnAttemptEvent(Entity entity, Location spawnLocation, SpawnReason spawnReason) {
        this.player = entity;
        this.spawnLocation = spawnLocation;
        this.spawnReason = spawnReason;
    }

    public Entity getSpawningEntity() {
        return player;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
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
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
