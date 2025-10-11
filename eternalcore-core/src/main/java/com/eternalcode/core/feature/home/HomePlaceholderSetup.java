package com.eternalcode.core.feature.home;

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
            PlaceholderReplacer.of("homes_owned", (text, targetPlayer) -> this.ownedHomes(targetPlayer)),
            PlaceholderReplacer.of("homes_count", (text, targetPlayer) -> this.homesCount(targetPlayer)),
            PlaceholderReplacer.of("homes_limit", (text, targetPlayer) -> this.homesLimit(targetPlayer)),
            PlaceholderReplacer.of("homes_left", (text, targetPlayer) -> this.homesLeft(targetPlayer))
        ).forEach(placeholderRegistry::registerPlaceholder);
    }

    private String homesLeft(Player targetPlayer) {
        int homesLimit = this.homeService.getHomeLimit(targetPlayer);
        int amountOfHomes = this.homeService.getAmountOfHomes(targetPlayer.getUniqueId());

        return homesLeft(homesLimit, amountOfHomes);
    }

    static String homesLeft(int homesLimit, int amountOfHomes) {
        if (homesLimit < -1 || amountOfHomes > homesLimit) {
            return "0";
        }

        int result = homesLimit - amountOfHomes;

        return String.valueOf(result);
    }

    private String ownedHomes(Player targetPlayer) {
        Collection<Home> homes = this.homeService.getHomes(targetPlayer.getUniqueId());
        Translation translation = this.translationManager.getMessages(targetPlayer.getUniqueId());

        if (homes.isEmpty()) {
            return translation.home().noHomesOwnedPlaceholder();
        }

        return homes.stream().map(Home::getName).collect(Collectors.joining(", "));
    }

    private String homesCount(Player targetPlayer) {
        return String.valueOf(this.homeService.getAmountOfHomes(targetPlayer.getUniqueId()));
    }

    private String homesLimit(Player targetPlayer) {
        return String.valueOf(this.homeService.getHomeLimit(targetPlayer));
    }
}
