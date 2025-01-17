package com.eternalcode.core.feature.language;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Service
class BukkitLanguageProvider implements LanguageProvider {

    private static Method LOCALE_METHOD;

    private final Server server;

    @Inject
    BukkitLanguageProvider(Server server) {
        this.server = server;
    }

    @Override
    public Language getDefaultLanguage(UUID player) {
        Player serverPlayer = server.getPlayer(player);

        if (serverPlayer == null) {
            return Language.DEFAULT;
        }

        return Language.fromLocale(getLocale(serverPlayer));
    }

    private Locale getLocale(Player player) {
        try {
            // Someday, when we use Paper API, we can remove this
            if (LOCALE_METHOD == null) {
                LOCALE_METHOD = Player.class.getMethod("locale");
            }
            return (Locale) LOCALE_METHOD.invoke(player);
        }
        catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException exception) {
            return Locale.of(player.getLocale());
        }
    }

}
