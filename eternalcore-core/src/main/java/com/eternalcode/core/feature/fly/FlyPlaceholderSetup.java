package com.eternalcode.core.feature.fly;

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
    category = "Fly",
    placeholders = {
        @PlaceholdersDocs.Entry(
            name = "fly",
            description = "Returns `true` if the player has fly mode enabled, `false` otherwise.",
            returnType = Type.BOOLEAN,
            requiresPlayer = true
        ),
        @PlaceholdersDocs.Entry(
            name = "fly_formatted",
            description = "Returns a localized fly mode status (the same enable/disable format used in messages), based on the player's language settings.",
            returnType = Type.STRING,
            requiresPlayer = true
        )
    }
)
@Controller
class FlyPlaceholderSetup {

    private final TranslationManager translationManager;

    @Inject
    FlyPlaceholderSetup(TranslationManager translationManager) {
        this.translationManager = translationManager;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUp(PlaceholderRegistry placeholders) {
        placeholders.register(Placeholder.ofBoolean("fly", target -> target.getAllowFlight()));
        placeholders.register(Placeholder.of("fly_formatted", target -> {
            Translation translation = this.translationManager.getMessages(target.getUniqueId());

            return target.getAllowFlight()
                ? translation.format().enable()
                : translation.format().disable();
        }));
    }
}
