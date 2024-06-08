package com.eternalcode.core.feature.home;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Controller;
import com.eternalcode.core.placeholder.PlaceholderRegistry;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import com.eternalcode.core.publish.Subscribe;
import com.eternalcode.core.publish.Subscriber;
import com.eternalcode.core.publish.event.EternalInitializeEvent;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.entity.Player;

@Controller
class HomePlaceholderSetup implements Subscriber {

    private final HomeService homeService;
    private final UserManager userManager;
    private final TranslationManager translationManager;
    private final PluginConfiguration pluginConfiguration;

    @Inject
    HomePlaceholderSetup(
        HomeService homeService,
        UserManager userManager,
        TranslationManager translationManager,
        PluginConfiguration pluginConfiguration
    ) {
        this.homeService = homeService;
        this.userManager = userManager;
        this.translationManager = translationManager;
        this.pluginConfiguration = pluginConfiguration;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUp(PlaceholderRegistry placeholderRegistry) {
        Stream.of(
            PlaceholderReplacer.of("homes_owned", (text, targetPlayer) -> this.ownedHomes(targetPlayer)),
            PlaceholderReplacer.of("homes_count", (text, targetPlayer) -> this.homesCount(targetPlayer)),
            PlaceholderReplacer.of("homes_limit", (text, targetPlayer) -> this.homesLimit(targetPlayer)),
            PlaceholderReplacer.of("homes_left", (text, targetPlayer) -> this.homesLeft(targetPlayer))
        ).forEach(placeholder -> placeholderRegistry.registerPlaceholder(placeholder));
    }

    private String homesLeft(Player targetPlayer) {
        int homesLimit = this.homeService.getHomeLimit(targetPlayer, this.pluginConfiguration.homes.maxHomes);
        int amountOfHomes = this.homeService.getAmountOfHomes(targetPlayer.getUniqueId());

        return homesLeft(homesLimit, amountOfHomes);
    }

    static String homesLeft(int homesLimit, int amountOfHomes) {
        if (homesLimit < -1) {
            return "0";
        }

        int result = homesLimit - amountOfHomes;

        return String.valueOf(result);
    }

    private String ownedHomes(Player targetPlayer) {
        Collection<Home> homes = this.homeService.getHomes(targetPlayer.getUniqueId());

        User user = this.userManager.getOrCreate(targetPlayer.getUniqueId(), targetPlayer.getName());

        Translation translation = this.translationManager.getMessages(user);

        if (homes.isEmpty()) {
            return translation.home().noHomesOwnedPlaceholder();
        }

        return homes.stream().map(Home::getName).collect(Collectors.joining(", "));
    }

    private String homesCount(Player targetPlayer) {
        return String.valueOf(this.homeService.getAmountOfHomes(targetPlayer.getUniqueId()));
    }

    private String homesLimit(Player targetPlayer) {
        return String.valueOf(this.homeService.getHomeLimit(targetPlayer, this.pluginConfiguration.homes.maxHomes));
    }
}
