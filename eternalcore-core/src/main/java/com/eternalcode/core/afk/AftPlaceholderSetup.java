package com.eternalcode.core.afk;

import com.eternalcode.core.event.EternalCoreInitializeEvent;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;

@Subscriber
public class AftPlaceholderSetup {

    @Subscribe(EternalCoreInitializeEvent.class)
    void setUpPlaceholders(PlaceholderRegistry placeholderRegistry, AfkService afkService) {
        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of("afk", player -> String.valueOf(afkService.isAfk(player.getUniqueId()))));
    }

}
