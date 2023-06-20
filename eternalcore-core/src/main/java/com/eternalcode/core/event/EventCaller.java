package com.eternalcode.core.event;

import org.bukkit.Server;
import org.bukkit.event.Event;

public class EventCaller {

    private final Server server;

    public EventCaller(Server server) {
        this.server = server;
    }

    public <T extends Event> T callEvent(T event) {
        this.server.getPluginManager().callEvent(event);

        return event;
    }

}
