package com.eternalcode.core.feature.msg;

import com.eternalcode.core.feature.msg.toggle.MsgState;
import com.eternalcode.core.feature.msg.toggle.MsgToggleRepository;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import com.eternalcode.core.placeholder.cache.AsyncPlaceholderCacheRegistry;
import com.eternalcode.core.placeholder.cache.AsyncPlaceholderCached;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import java.time.Duration;
import java.util.UUID;
import java.util.function.Function;

@Controller
public class MsgPlaceholderSetup {

    public static final String MSG_STATE_CACHE_KEY = "msg_state";

    private final MsgService msgService;
    private final MsgToggleRepository msgToggleRepository;
    private final TranslationManager translationManager;
    private final AsyncPlaceholderCacheRegistry cacheRegistry;

    @Inject
    MsgPlaceholderSetup(
        MsgService msgService,
        MsgToggleRepository msgToggleRepository,
        TranslationManager translationManager,
        AsyncPlaceholderCacheRegistry cacheRegistry
    ) {
        this.msgService = msgService;
        this.msgToggleRepository = msgToggleRepository;
        this.translationManager = translationManager;
        this.cacheRegistry = cacheRegistry;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUpPlaceholders(PlaceholderRegistry placeholderRegistry) {
        Translation translation = this.translationManager.getMessages();

        AsyncPlaceholderCached<MsgState> stateCache = this.cacheRegistry.register(
            MSG_STATE_CACHE_KEY,
            this.msgToggleRepository::getPrivateChatState,
            Duration.ofMinutes(10)
        );

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
            player -> this.formatMsgState(
                player.getUniqueId(),
                stateCache,
                translation,
                state -> state.name().toLowerCase()
            )
        ));

        placeholderRegistry.registerPlaceholder(PlaceholderReplacer.of(
            "msg_status_formatted",
            player -> this.formatMsgState(
                player.getUniqueId(),
                stateCache,
                translation,
                state -> state == MsgState.ENABLED
                    ? translation.msg().placeholders().msgEnabled()
                    : translation.msg().placeholders().msgDisabled()
            )
        ));
    }

    private String formatMsgState(
        UUID uuid,
        AsyncPlaceholderCached<MsgState> stateCache,
        Translation translation,
        Function<MsgState, String> formatter
    ) {
        MsgState state = stateCache.getCached(uuid);

        if (state == null) {
            return translation.msg().placeholders().loading();
        }

        return formatter.apply(state);
    }
}
