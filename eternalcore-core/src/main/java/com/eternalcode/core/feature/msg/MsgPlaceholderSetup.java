package com.eternalcode.core.feature.msg;

import com.eternalcode.core.feature.msg.toggle.MsgState;
import com.eternalcode.core.feature.msg.toggle.MsgToggleService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import java.util.UUID;

@Controller
class MsgPlaceholderSetup {

    private final MsgService msgService;
    private final MsgToggleService msgToggleService;

    @Inject
    MsgPlaceholderSetup(MsgService msgService, MsgToggleService msgToggleService) {
        this.msgService = msgService;
        this.msgToggleService = msgToggleService;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUpPlaceholders(PlaceholderRegistry placeholderRegistry) {
        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "socialspy_status",
            player -> {
                UUID uuid = player.getUniqueId();
                return String.valueOf(this.msgService.isSpy(uuid));
            }
        ));

        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "msg_toggle",
            player -> {
                UUID uuid = player.getUniqueId();
                MsgState state = this.msgToggleService.getState(uuid).join();
                return state.name().toLowerCase();
            }
        ));
    }
}
