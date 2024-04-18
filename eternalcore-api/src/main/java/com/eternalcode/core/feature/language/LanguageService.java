package com.eternalcode.core.feature.language;

import java.util.Locale;
import org.bukkit.entity.Player;

public interface LanguageService {

    Locale getPlayerLanguage(Player player);

    void setPlayerLanguage(Player player, Locale locale);
}
