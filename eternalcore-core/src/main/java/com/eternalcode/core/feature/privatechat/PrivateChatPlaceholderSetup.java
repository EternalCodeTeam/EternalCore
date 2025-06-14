package com.eternalcode.core.feature.privatechat;

import com.eternalcode.core.feature.privatechat.toggle.PrivateChatState;
import com.eternalcode.core.feature.privatechat.toggle.PrivateChatStateService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.AsyncPlaceholderValue;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;

import java.util.concurrent.CompletableFuture;
import java.util.UUID;

@Controller
public class PrivateChatPlaceholderSetup {

    private final PrivateChatStateService privateChatStateService;
    private final TranslationManager translationManager;

    @Inject
    public PrivateChatPlaceholderSetup(
        PrivateChatStateService privateChatStateService,
        TranslationManager translationManager
    ) {
        this.privateChatStateService = privateChatStateService;
        this.translationManager = translationManager;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUp(PlaceholderRegistry placeholderRegistry) {
        placeholderRegistry.registerPlaceholder(
            PlaceholderReplacer.of("eternalcore_msgtoggle", (text, targetPlayer) -> {
                UUID playerId = targetPlayer.getUniqueId();
                CompletableFuture<PrivateChatState> stateFuture = this.privateChatStateService.getChatState(playerId);

                return new AsyncPlaceholderValue<>(
                    stateFuture,
                    state -> state == PrivateChatState.ENABLE ? "true" : "false"
                ).toString();
            })
        );

        placeholderRegistry.registerPlaceholder(
            PlaceholderReplacer.of("eternalcore_msgtoggle_formatted", (text, targetPlayer) -> {
                UUID playerId = targetPlayer.getUniqueId();
                Translation messages = this.translationManager.getMessages(playerId);
                CompletableFuture<PrivateChatState> stateFuture = this.privateChatStateService.getChatState(playerId);

                return new AsyncPlaceholderValue<>(
                    stateFuture,
                    state -> state == PrivateChatState.ENABLE
                        ? messages.privateChat().privateMessageEnabledPlaceholder()
                        : messages.privateChat().privateMessageDisabledPlaceholder()
                ).toString();
            })
        );
    }
}
