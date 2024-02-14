package com.eternalcode.core.feature.helpop.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HelpOpEvent extends Event implements Cancellable {

    private static final HandlerList HELP_OP_HANDLER_LIST = new HandlerList();

    private final Player player;
    private final String content;
    private boolean cancelled;

    public HelpOpEvent(Player player, String content) {
        super(false);

        this.player = player;
        this.content = content;
    }

    public static HandlerList getHelpOpHandlerList() {
        return HELP_OP_HANDLER_LIST;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getContent() {
        return this.content;
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
        return HELP_OP_HANDLER_LIST;
    }
}
