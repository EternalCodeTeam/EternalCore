package com.eternalcode.example.feature.afk;

import com.eternalcode.core.feature.afk.event.AfkSwitchEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

public class ApiAfkListener implements Listener {

    private static final Logger LOGGER = Logger.getLogger(ApiAfkListener.class.getName());

    @EventHandler
    void afk(AfkSwitchEvent event) {
        event.setCancelled(true);
        LOGGER.info("Afk event has been cancelled via EternalCore developer api bridge.");
    }
}
