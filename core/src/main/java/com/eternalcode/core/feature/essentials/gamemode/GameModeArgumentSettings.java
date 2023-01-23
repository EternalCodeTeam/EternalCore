package com.eternalcode.core.feature.essentials.gamemode;

import org.bukkit.GameMode;

import java.util.Collection;
import java.util.Optional;

public interface GameModeArgumentSettings {

    Optional<GameMode> getByAlias(String alias);

    Collection<String> getAvailableAliases();

}
