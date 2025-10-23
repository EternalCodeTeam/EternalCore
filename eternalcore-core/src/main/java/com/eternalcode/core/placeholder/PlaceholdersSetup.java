package com.eternalcode.core.placeholder;

import com.eternalcode.annotations.scan.placeholder.PlaceholdersDocs;
import com.eternalcode.annotations.scan.placeholder.PlaceholdersDocs.Entry;
import com.eternalcode.annotations.scan.placeholder.PlaceholdersDocs.Entry.Type;
import com.eternalcode.core.configuration.implementation.PlaceholdersConfiguration;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.publish.Subscribe;
import org.bukkit.Server;

@PlaceholdersDocs(
    placeholders = {
        @Entry(
            name = "online",
            description = "Returns the number of online players who are not vanished",
            returnType = Type.INT,
            requiresPlayer = false
        )
    }
)
@Controller
class PlaceholdersSetup {

    @Subscribe(EternalInitializeEvent.class)
    void setUp(PlaceholderRegistry placeholders, PlaceholdersConfiguration config) {
        for (String key : config.placeholders.keySet()) {
            placeholders.register(Placeholder.of(key, player -> config.placeholders.getOrDefault(key, "{" + key + "}")));
        }
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUpPlaceholders(PlaceholderRegistry placeholders, Server server, VanishService vanishService) {
        placeholders.register(Placeholder.ofLong("online", player ->
            server.getOnlinePlayers()
                .stream()
                .filter(onlinePlayer -> !vanishService.isVanished(onlinePlayer))
                .count()
            )
        );
    }
}
