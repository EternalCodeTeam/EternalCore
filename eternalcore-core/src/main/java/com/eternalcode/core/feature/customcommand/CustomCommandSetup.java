package com.eternalcode.core.feature.customcommand;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;

@Controller
class CustomCommandSetup {

    private final CustomCommandRegistry customCommandRegistry;

    @Inject
    CustomCommandSetup(CustomCommandRegistry customCommandRegistry) {
        this.customCommandRegistry = customCommandRegistry;
    }

    @Subscribe(EternalInitializeEvent.class)
    public void onInitialize() {
        this.customCommandRegistry.registerCustomCommands();
    }
}
