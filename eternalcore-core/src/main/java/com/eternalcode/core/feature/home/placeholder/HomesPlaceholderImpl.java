package com.eternalcode.core.feature.home.placeholder;

import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.feature.home.HomeManager;
import com.eternalcode.core.placeholder.PlaceholderReplacer;
import com.eternalcode.core.translation.Translation;
import com.eternalcode.core.translation.TranslationManager;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.stream.Collectors;

public class HomesPlaceholderImpl implements PlaceholderReplacer {

    private final HomeManager homeManager;
    private final UserManager userManager;
    private final TranslationManager translationManager;

    public HomesPlaceholderImpl(HomeManager homeManager, UserManager userManager, TranslationManager translationManager) {
        this.homeManager = homeManager;
        this.userManager = userManager;
        this.translationManager = translationManager;
    }

    @Override
    public String apply(String text, Player targetPlayer) {
        Collection<Home> homes = this.homeManager.getHomes(targetPlayer.getUniqueId());

        User user = this.userManager.getOrCreate(targetPlayer.getUniqueId(), targetPlayer.getName());

        Translation translation = this.translationManager.getMessages(user);

        if (homes.isEmpty()) {
            return translation.placeholder().homeNotFound();
        }

        return homes.stream().map(Home::getName).collect(Collectors.joining(", "));
    }
}
