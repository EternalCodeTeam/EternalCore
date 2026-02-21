package com.eternalcode.core.feature.home;

import com.eternalcode.annotations.scan.placeholder.PlaceholdersDocs;
import com.eternalcode.annotations.scan.placeholder.PlaceholdersDocs.Entry.Type;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import java.util.Collection;
import java.util.stream.Collectors;

@PlaceholdersDocs(
    category = "Home",
    placeholders = {
        @PlaceholdersDocs.Entry(
            name = "homes_owned",
            description = "Returns a comma-separated list of all homes owned by the player. If the player has no homes, returns a localized message. e.g. `home1, home2, domek`",
            returnType = Type.STRING,
            requiresPlayer = true
        ),
        @PlaceholdersDocs.Entry(
            name = "homes_count",
            description = "Returns the number of homes the player currently owns.",
            returnType = Type.INT,
            requiresPlayer = true
        ),
        @PlaceholdersDocs.Entry(
            name = "homes_limit",
            description = "Returns the maximum number of homes the player can have.",
            returnType = Type.INT,
            requiresPlayer = true
        ),
        @PlaceholdersDocs.Entry(
            name = "homes_left",
            description = "Returns how many more homes the player can create before reaching the limit.",
            returnType = Type.INT,
            requiresPlayer = true
        )
    }
)
@Controller
class HomePlaceholderSetup {

    private final HomeService homeService;
    private final TranslationManager translationManager;

    @Inject
    HomePlaceholderSetup(HomeService homeService, TranslationManager translationManager) {
        this.homeService = homeService;
        this.translationManager = translationManager;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUp(PlaceholderRegistry placeholders) {
        placeholders.register(Placeholder.of("homes_owned", target -> {
            Collection<Home> homes = this.homeService.getHomes(target.getUniqueId());
            Translation translation = this.translationManager.getMessages(target.getUniqueId());

            if (homes.isEmpty()) {
                return translation.home().noHomesOwnedPlaceholder();
            }

            return homes.stream()
                .map(Home::getName)
                .collect(Collectors.joining(", "));
        }));
        placeholders.register(Placeholder.ofInt("homes_count", target -> this.homeService.getAmountOfHomes(target.getUniqueId())));
        placeholders.register(Placeholder.ofInt("homes_limit", target -> this.homeService.getHomeLimit(target)));
        placeholders.register(Placeholder.ofInt("homes_left", target -> {
            int homesLimit = this.homeService.getHomeLimit(target);
            int amountOfHomes = this.homeService.getAmountOfHomes(target.getUniqueId());

            if (homesLimit < -1 || amountOfHomes > homesLimit) {
                return 0;
            }

            return homesLimit - amountOfHomes;
        }));
    }
}
