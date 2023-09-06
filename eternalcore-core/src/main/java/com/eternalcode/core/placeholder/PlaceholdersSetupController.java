package com.eternalcode.core.placeholder;

import com.eternalcode.core.configuration.implementation.PlaceholdersConfiguration;
import com.eternalcode.core.injector.annotations.component.Controller;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.Map;

@Controller
class PlaceholdersSetupController implements Listener {

    private final PlaceholderRegistry placeholderRegistry;
    private final PlaceholdersConfiguration placeholdersConfiguration;

    public PlaceholdersSetupController(PlaceholderRegistry placeholderRegistry, PlaceholdersConfiguration placeholdersConfiguration) {
        this.placeholderRegistry = placeholderRegistry;
        this.placeholdersConfiguration = placeholdersConfiguration;
    }

    @EventHandler
    public void setup(PluginEnableEvent event) {
        this.placeholderRegistry.registerPlaceholderReplacer(text -> {
            for (Map.Entry<String, String> entry : this.placeholdersConfiguration.placeholders.entrySet()) {
                text = text.replace(entry.getKey(), entry.getValue());
            }

            return text;
        });
    }

}
