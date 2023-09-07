package com.eternalcode.core.afk;

import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import com.eternalcode.core.publish.Subscribe;

public class AftPlaceholderSetup {

    @Subscribe(EternalInitializeEvent.class)
    void setUpPlaceholders(PlaceholderRegistry placeholderRegistry, AfkService afkService) {
        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of("afk", player -> String.valueOf(afkService.isAfk(player.getUniqueId()))));
    }

}
