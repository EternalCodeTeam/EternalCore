package com.eternalcode.core.feature.home;

import com.eternalcode.annotations.scan.placeholder.PlaceholderDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.entity.Player;

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
    void setUp(PlaceholderRegistry placeholderRegistry) {
        Stream.of(
            this.createHomesOwnedPlaceholder(),
            this.createHomesCountPlaceholder(),
            this.createHomesLimitPlaceholder(),
            this.createHomesLeftPlaceholder()
        ).forEach(placeholderRegistry::registerPlaceholder);
    }

    @PlaceholderDocs(
        name = "homes_owned",
        description = "Returns a comma-separated list of all homes owned by the player. If the player has no homes, returns a localized message.",
        example = "home1, home2, domek",
        returnType = "String",
        category = "Home",
        requiresPlayer = true
    )
    private PlaceholderReplacer createHomesOwnedPlaceholder() {
        return PlaceholderReplacer.of(
            "homes_owned",
            (text, targetPlayer) -> {
                Collection<Home> homes = this.homeService.getHomes(targetPlayer.getUniqueId());
                Translation translation = this.translationManager.getMessages(targetPlayer.getUniqueId());

                if (homes.isEmpty()) {
                    return translation.home().noHomesOwnedPlaceholder();
                }

                return homes.stream()
                    .map(Home::getName)
                    .collect(Collectors.joining(", "));
            }
        );
    }

    @PlaceholderDocs(
        name = "homes_count",
        description = "Returns the number of homes the player currently owns.",
        example = "3",
        returnType = "int",
        category = "Home",
        requiresPlayer = true
    )
    private PlaceholderReplacer createHomesCountPlaceholder() {
        return PlaceholderReplacer.of(
            "homes_count",
            (text, targetPlayer) ->
                String.valueOf(this.homeService.getAmountOfHomes(targetPlayer.getUniqueId()))
        );
    }

    @PlaceholderDocs(
        name = "homes_limit",
        description = "Returns the maximum number of homes the player can have.",
        example = "5",
        returnType = "int",
        category = "Home",
        requiresPlayer = true
    )
    private PlaceholderReplacer createHomesLimitPlaceholder() {
        return PlaceholderReplacer.of(
            "homes_limit",
            (text, targetPlayer) ->
                String.valueOf(this.homeService.getHomeLimit(targetPlayer))
        );
    }

    @PlaceholderDocs(
        name = "homes_left",
        description = "Returns how many more homes the player can create before reaching the limit.",
        example = "2",
        returnType = "int",
        category = "Home",
        requiresPlayer = true
    )
    private PlaceholderReplacer createHomesLeftPlaceholder() {
        return PlaceholderReplacer.of(
            "homes_left",
            (text, targetPlayer) -> {
                int homesLimit = this.homeService.getHomeLimit(targetPlayer);
                int amountOfHomes = this.homeService.getAmountOfHomes(targetPlayer.getUniqueId());

                if (homesLimit < -1 || amountOfHomes > homesLimit) {
                    return "0";
                }

                return String.valueOf(homesLimit - amountOfHomes);
            }
        );
    }
}
