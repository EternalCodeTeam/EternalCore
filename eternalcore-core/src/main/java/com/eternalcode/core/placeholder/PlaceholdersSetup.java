package com.eternalcode.core.placeholder;

import com.eternalcode.core.configuration.implementation.PlaceholdersConfiguration;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.publish.Subscribe;
import org.bukkit.Server;

class PlaceholdersSetup {

    @Subscribe(EternalInitializeEvent.class)
    void setUp(PlaceholderRegistry placeholderRegistry, PlaceholdersConfiguration placeholdersConfiguration) {
        placeholdersConfiguration.placeholders.forEach((key, value) -> {
            placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(key, value));
        });
    }


    @Subscribe(EternalInitializeEvent.class)
    void setUpPlaceholders(PlaceholderRegistry placeholderRegistry, Server server) {
        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of("online", player -> String.valueOf(server.getOnlinePlayers().size())));
    }

}
