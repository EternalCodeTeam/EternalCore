package com.eternalcode.core.feature.home;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.eternalcode.commons.bukkit.position.Position;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public interface HomeService {

    int getAmountOfHomes(UUID playerUniqueId);

    Collection<Home> getHomes(UUID playerUniqueId);

    Optional<Home> getHome(UUID playerUniqueId, String name);

    boolean hasHome(UUID playerUniqueId, String name);

    boolean hasHome(UUID playerUniqueId, Home home);

    void deleteHome(UUID playerUniqueId, String name);

    @Nullable
    Home createHome(UUID playerUniqueId, String name, Position position);

    int getHomeLimit(Player player);
}
