package com.eternalcode.core.feature.randomteleport.event;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class RandomSafeLocationCandidateEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Location candidateLocation;
    private boolean isCancelled;

    public RandomSafeLocationCandidateEvent(Location candidateLocation) {
        super(false);
        this.candidateLocation = candidateLocation;
    }

    public Location getCandidateLocation() {
        return this.candidateLocation.clone();
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
