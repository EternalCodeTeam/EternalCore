package com.eternalcode.core.feature.language;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import com.eternalcode.core.user.User;
import com.eternalcode.core.user.UserManager;
import java.util.Locale;
import java.util.Optional;
import org.bukkit.entity.Player;

@Service
public class LanguageServiceImpl implements LanguageService {

    private final UserManager userManager;

    @Inject
    public LanguageServiceImpl(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public Locale getPlayerLanguage(Player player) {
        Optional<User> user = this.userManager.getUser(player.getUniqueId());

        if (user.isPresent()) {
            Language language = user.get().getLanguage();
            return language.toLocale();
        }

        return Language.DEFAULT.toLocale();
    }

    @Override
    public void setPlayerLanguage(Player player, Locale locale) {
        Optional<User> user = this.userManager.getUser(player.getUniqueId());

        user.ifPresent(value -> value.getSettings().setLanguage(Language.fromLocale(locale)));
    }
}
