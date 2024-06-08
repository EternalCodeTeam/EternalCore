package com.eternalcode.core.feature.home;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface HomeService {

    int getAmountOfHomes(UUID playerUniqueId);

    Collection<Home> getHomes(UUID playerUniqueId);

    Optional<Home> getHome(UUID uniqueId, String name);

    boolean hasHomeWithSpecificName(UUID playerUniqueId, String name);

    void deleteHome(UUID playerUniqueId, String name);

    Home createHome(UUID playerUniqueId, String name, Location location);

    int getHomeLimit(Player player, Map<String, Integer> maxHomes);
}
