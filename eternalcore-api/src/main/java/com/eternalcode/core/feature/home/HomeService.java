package com.eternalcode.core.feature.home;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public interface HomeService {

    Optional<Home> getHome(UUID playerUniqueId);

    Optional<Home> getHome(UUID playerUniqueId, String name);

    Optional<Set<Home>> getHomes(UUID playerUniqueId);

    boolean hasHomes(UUID playerUniqueId);

    void deleteHome(UUID playerUniqueId, String name);

    boolean underLimit(UUID playerUniqueId);

    Home setHome(UUID playerUniqueId, String name, Location location);

    int getHomeLimit(UUID playerUniqueId);

}
