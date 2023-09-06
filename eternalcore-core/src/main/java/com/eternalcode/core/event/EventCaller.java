package com.eternalcode.core.event;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import org.bukkit.Server;
import org.bukkit.event.Event;

@Service
public class EventCaller {

    private final Server server;

    @Inject
    public EventCaller(Server server) {
        this.server = server;
    }

    public <T extends Event> T callEvent(T event) {
        this.server.getPluginManager().callEvent(event);

        return event;
    }

}
