package com.eternalcode.core.feature.home.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class HomeLimitReachedEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID playerUniqueId;
    private final int maxLimit;
    private final int limitAmount;

    public HomeLimitReachedEvent(UUID playerUniqueId, int maxLimit, int limitAmount) {
        super(false);

        this.playerUniqueId = playerUniqueId;
        this.maxLimit = maxLimit;
        this.limitAmount = limitAmount;
    }

    public UUID getPlayerUniqueId() {
        return this.playerUniqueId;
    }

    public int getMaxLimit() {
        return maxLimit;
    }

    public int getLimitAmount() {
        return limitAmount;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
