package com.eternalcode.core.afk;

import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import com.eternalcode.core.publish.Subscribe;

@Controller
class AftPlaceholderSetup implements Subscriber {

    @Subscribe(EternalInitializeEvent.class)
    void setUpPlaceholders(PlaceholderRegistry placeholderRegistry, AfkService afkService) {
        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of("afk", player -> String.valueOf(afkService.isAfk(player.getUniqueId()))));
    }

}
