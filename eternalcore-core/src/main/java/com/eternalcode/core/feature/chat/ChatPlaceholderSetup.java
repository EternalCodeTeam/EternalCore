package com.eternalcode.core.feature.chat;

import com.eternalcode.annotations.scan.placeholder.PlaceholdersDocs;
import com.eternalcode.annotations.scan.placeholder.PlaceholdersDocs.Entry.Type;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.Placeholder;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.util.DurationUtil;
import java.time.Duration;
import java.util.UUID;

@PlaceholdersDocs(
    category = "Chat",
    placeholders = {
        @PlaceholdersDocs.Entry(
            name = "chat_enabled",
            description = "Returns `true` if the chat is currently enabled on the server, `false` otherwise.",
            returnType = Type.BOOLEAN,
            requiresPlayer = false
        ),
        @PlaceholdersDocs.Entry(
            name = "chat_enabled_formatted",
            description = "Returns a localized chat status (the same enable/disable format used in messages), based on the player's language settings.",
            returnType = Type.STRING,
            requiresPlayer = true
        ),
        @PlaceholdersDocs.Entry(
            name = "chat_slowmode",
            description = "Returns the server-wide chat slowmode delay in seconds. Returns 0 if slowmode is disabled.",
            returnType = Type.STRING,
            requiresPlayer = false
        ),
        @PlaceholdersDocs.Entry(
            name = "chat_slowmode_formatted",
            description = "Returns the server-wide chat slowmode delay as a formatted duration. e.g. `5s`",
            returnType = Type.STRING,
            requiresPlayer = false
        ),
        @PlaceholdersDocs.Entry(
            name = "chat_slowmode_remaining",
            description = "Returns how many seconds the player has to wait before sending the next message. Returns 0 if the player can chat right now.",
            returnType = Type.STRING,
            requiresPlayer = true
        ),
        @PlaceholdersDocs.Entry(
            name = "chat_slowmode_remaining_formatted",
            description = "Returns the remaining time until the player can send the next message as a formatted duration. e.g. `3s`",
            returnType = Type.STRING,
            requiresPlayer = true
        )
    }
)
@Controller
class ChatPlaceholderSetup {

    private final ChatSettings chatSettings;
    private final ChatService chatService;
    private final TranslationManager translationManager;

    @Inject
    ChatPlaceholderSetup(ChatSettings chatSettings, ChatService chatService, TranslationManager translationManager) {
        this.chatSettings = chatSettings;
        this.chatService = chatService;
        this.translationManager = translationManager;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUp(PlaceholderRegistry placeholders) {
        placeholders.register(Placeholder.ofBoolean("chat_enabled", target -> this.chatSettings.chatEnabled()));
        placeholders.register(Placeholder.of("chat_enabled_formatted", target -> {
            Translation translation = this.translationManager.getMessages(target.getUniqueId());

            return this.chatSettings.chatEnabled()
                ? translation.format().enable()
                : translation.format().disable();
        }));

        placeholders.register(Placeholder.ofLong("chat_slowmode", target -> this.chatSettings.chatDelay().toSeconds()));
        placeholders.register(Placeholder.of("chat_slowmode_formatted", target -> DurationUtil.format(this.chatSettings.chatDelay(), true)));

        placeholders.register(Placeholder.ofLong("chat_slowmode_remaining", target -> this.getRemainingSlowMode(target.getUniqueId()).toSeconds()));
        placeholders.register(Placeholder.of("chat_slowmode_remaining_formatted", target -> DurationUtil.format(this.getRemainingSlowMode(target.getUniqueId()), true)));
    }

    private Duration getRemainingSlowMode(UUID uniqueId) {
        if (!this.chatService.hasSlowedChat(uniqueId)) {
            return Duration.ZERO;
        }

        return this.chatService.getRemainingSlowDown(uniqueId);
    }
}
