package com.eternalcode.core.feature.godmode;

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
    category = "GodMode",
    placeholders = {
        @PlaceholdersDocs.Entry(
            name = "godmode",
            description = "Returns `true` if the player has god mode (invulnerability) enabled, `false` otherwise.",
            returnType = Type.BOOLEAN,
            requiresPlayer = true
        ),
        @PlaceholdersDocs.Entry(
            name = "godmode_formatted",
            description = "Returns a localized god mode status (the same enable/disable format used in messages), based on the player's language settings.",
            returnType = Type.STRING,
            requiresPlayer = true
        )
    }
)
@Controller
class GodModePlaceholderSetup {

    private final TranslationManager translationManager;

    @Inject
    GodModePlaceholderSetup(TranslationManager translationManager) {
        this.translationManager = translationManager;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUp(PlaceholderRegistry placeholders) {
        placeholders.register(Placeholder.ofBoolean("godmode", target -> target.isInvulnerable()));
        placeholders.register(Placeholder.of("godmode_formatted", target -> {
            Translation translation = this.translationManager.getMessages(target.getUniqueId());

            return target.isInvulnerable()
                ? translation.format().enable()
                : translation.format().disable();
        }));
    }
}
