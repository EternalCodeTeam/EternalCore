package com.eternalcode.core.feature.msg;

import com.eternalcode.core.feature.msg.toggle.MsgState;
import com.eternalcode.core.feature.msg.toggle.MsgToggleRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.Placeholder;
import com.eternalcode.core.placeholder.watcher.PlaceholderWatcherKey;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import java.util.UUID;

@Controller
public class MsgPlaceholderSetup {

    public static final PlaceholderWatcherKey<MsgState> MSG_STATE = PlaceholderWatcherKey.of("msg_state", MsgState.class);

    private final MsgService msgService;
    private final MsgToggleRepository msgToggleRepository;
    private final TranslationManager translationManager;

    @Inject
    MsgPlaceholderSetup(
        MsgService msgService,
        MsgToggleRepository msgToggleRepository,
        TranslationManager translationManager
    ) {
        this.msgService = msgService;
        this.msgToggleRepository = msgToggleRepository;
        this.translationManager = translationManager;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUpPlaceholders(PlaceholderRegistry registry) {
        Translation translation = this.translationManager.getMessages();

        registry.registerPlaceholder(Placeholder.of(
            "socialspy_status",
            player -> String.valueOf(this.msgService.isSpy(player.getUniqueId()))
        ));

        registry.registerPlaceholder(Placeholder.of(
            "socialspy_status_formatted",
            player -> {
                UUID uuid = player.getUniqueId();
                return this.msgService.isSpy(uuid)
                    ? translation.msg().placeholders().socialSpyEnabled()
                    : translation.msg().placeholders().socialSpyDisabled();
            }
        ));

        registry.registerPlaceholder(Placeholder.async(
            "msg_status",
            MSG_STATE,
            player -> msgToggleRepository.getPrivateChatState(player),
            state -> state.name().toLowerCase()
        ));

        registry.registerPlaceholder(Placeholder.async(
            "msg_status_formatted",
            MSG_STATE,
            player -> msgToggleRepository.getPrivateChatState(player),
            state -> state == MsgState.ENABLED
                ? translation.msg().placeholders().msgEnabled()
                : translation.msg().placeholders().msgDisabled()
            )
        );
    }

}
