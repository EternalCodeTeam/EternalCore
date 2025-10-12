package com.eternalcode.core.feature.msg;

import com.eternalcode.core.feature.msg.toggle.MsgState;
import com.eternalcode.core.feature.msg.toggle.MsgToggleService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.cache.AsyncPlaceholderCached;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import java.util.UUID;

@Controller
class MsgPlaceholderSetup {

    private final MsgService msgService;
    private final TranslationManager translationManager;
    private final AsyncPlaceholderCached<MsgState> stateCache;

    @Inject
    MsgPlaceholderSetup(MsgService msgService, MsgToggleService msgToggleService, TranslationManager translationManager) {
        this.msgService = msgService;
        this.translationManager = translationManager;
        this.stateCache = new AsyncPlaceholderCached<>(msgToggleService::getState);
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUpPlaceholders(PlaceholderRegistry placeholderRegistry) {
        Translation translation = this.translationManager.getMessages();

        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "socialspy_status",
            player -> String.valueOf(this.msgService.isSpy(player.getUniqueId()))
        ));

        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "socialspy_status_formatted",
            player -> {
                UUID uuid = player.getUniqueId();
                return this.msgService.isSpy(uuid)
                    ? translation.msg().placeholders().socialSpyEnabled()
                    : translation.msg().placeholders().socialSpyDisabled();
            }
        ));

        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "msg_status",
            player -> {
                UUID uuid = player.getUniqueId();
                MsgState state = this.stateCache.getCached(uuid);

                if (state == null) {
                    return translation.msg().placeholders().loading();
                }

                return state.name().toLowerCase();
            }
        ));

        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "msg_status_formatted",
            player -> {
                UUID uuid = player.getUniqueId();
                MsgState state = this.stateCache.getCached(uuid);

                if (state == null) {
                    return translation.msg().placeholders().loading();
                }

                return state == MsgState.ENABLED
                    ? translation.msg().placeholders().msgEnabled()
                    : translation.msg().placeholders().msgDisabled();
            }
        ));
    }

    public AsyncPlaceholderCached<MsgState> getStateCache() {
        return this.stateCache;
    }
}
