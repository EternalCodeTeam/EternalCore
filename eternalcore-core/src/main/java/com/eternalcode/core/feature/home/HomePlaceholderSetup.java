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
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
class HomePlaceholderSetup implements Subscriber {

    private final HomeManager homeManager;
    private final UserManager userManager;
    private final TranslationManager translationManager;
    private final PluginConfiguration pluginConfiguration;

    @Inject
    HomePlaceholderSetup(HomeManager homeManager, UserManager userManager, TranslationManager translationManager, PluginConfiguration pluginConfiguration) {
        this.homeManager = homeManager;
        this.userManager = userManager;
        this.translationManager = translationManager;
        this.pluginConfiguration = pluginConfiguration;
    }

    @Subscribe(EternalInitializeEvent.class)
    void setUp(PlaceholderRegistry placeholderRegistry) {
        Stream.of(
            PlaceholderReplacer.of("homes_owned", (text, targetPlayer) -> this.ownedHomes(targetPlayer)),
            PlaceholderReplacer.of("homes_count", (text, targetPlayer) -> this.homesCount(targetPlayer)),
            PlaceholderReplacer.of("homes_limit", (text, targetPlayer) -> this.homesLimit(targetPlayer))
        ).forEach(placeholder -> placeholderRegistry.registerPlaceholder(placeholder));
    }

    private String ownedHomes(Player targetPlayer) {
        Collection<Home> homes = this.homeManager.getHomes(targetPlayer.getUniqueId());

        User user = this.userManager.getOrCreate(targetPlayer.getUniqueId(), targetPlayer.getName());

        Translation translation = this.translationManager.getMessages(user);

        if (homes.isEmpty()) {
            return translation.home().noHomesOwned();
        }

        return homes.stream().map(Home::getName).collect(Collectors.joining(", "));
    }

    private String homesCount(Player targetPlayer) {
        return String.valueOf(this.homeManager.getAmountOfHomes(targetPlayer.getUniqueId()));
    }

    private String homesLimit(Player targetPlayer) {
        return String.valueOf(this.homeManager.getHomesLimit(targetPlayer, this.pluginConfiguration.homes));
    }

}
