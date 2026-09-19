package com.eternalcode.core.feature.vanish;

import com.eternalcode.annotations.scan.placeholder.PlaceholdersDocs;
import com.eternalcode.annotations.scan.placeholder.PlaceholdersDocs.Entry.Type;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.Placeholder;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;

@PlaceholdersDocs(
    category = "Vanish",
    placeholders = {
        @PlaceholdersDocs.Entry(
            name = "is_vanished",
            description = "Returns `true` if the player is currently vanished, `false` otherwise.",
            returnType = Type.BOOLEAN,
            requiresPlayer = true
        ),
        @PlaceholdersDocs.Entry(
            name = "vanished_count",
            description = "Returns the number of currently vanished players.",
            returnType = Type.INT,
            requiresPlayer = false
        ),
        @PlaceholdersDocs.Entry(
            name = "vanished_list",
            description = "Returns a comma-separated list of all vanished players. If nobody is vanished, returns an empty string. e.g. `Steve, Alex`",
            returnType = Type.STRING,
            requiresPlayer = false
        )
    }
)
@Controller
class VanishPlaceholderSetup {

    private static final String NAMES_DELIMITER = ", ";

    private final VanishService vanishService;

    @Inject
    VanishPlaceholderSetup(VanishService vanishService) {
        this.vanishService = vanishService;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUp(PlaceholderRegistry placeholders) {
        placeholders.register(Placeholder.ofBoolean("is_vanished", target -> this.vanishService.isVanished(target)));
        placeholders.register(Placeholder.ofInt("vanished_count", target -> this.vanishService.getVanishedPlayers().size()));
        placeholders.register(Placeholder.of("vanished_list", target -> String.join(NAMES_DELIMITER, this.vanishService.getVanishedPlayerNames())));
    }
}
