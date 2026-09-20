package com.eternalcode.core.feature.adminchat;

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

@PlaceholdersDocs(
    category = "AdminChat",
    placeholders = {
        @PlaceholdersDocs.Entry(
            name = "adminchat",
            description = "Returns `true` if the player has persistent admin chat mode enabled, `false` otherwise.",
            returnType = Type.BOOLEAN,
            requiresPlayer = true
        ),
        @PlaceholdersDocs.Entry(
            name = "adminchat_formatted",
            description = "Returns a localized admin chat status (the same enable/disable format used in messages), based on the player's language settings.",
            returnType = Type.STRING,
            requiresPlayer = true
        )
    }
)
@Controller
class AdminChatPlaceholderSetup {

    private final AdminChatService adminChatService;
    private final TranslationManager translationManager;

    @Inject
    AdminChatPlaceholderSetup(AdminChatService adminChatService, TranslationManager translationManager) {
        this.adminChatService = adminChatService;
        this.translationManager = translationManager;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUp(PlaceholderRegistry placeholders) {
        placeholders.register(Placeholder.ofBoolean("adminchat", target -> this.adminChatService.hasEnabledChat(target.getUniqueId())));
        placeholders.register(Placeholder.of("adminchat_formatted", target -> {
            Translation translation = this.translationManager.getMessages(target.getUniqueId());

            return this.adminChatService.hasEnabledChat(target.getUniqueId())
                ? translation.format().enable()
                : translation.format().disable();
        }));
    }
}
