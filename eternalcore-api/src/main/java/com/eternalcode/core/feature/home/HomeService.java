package com.eternalcode.core.feature.home;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface HomeService {

    CompletableFuture<Optional<Home>> getHome(Player player, String homeName);

    CompletableFuture<Void> createHome(String name, UUID owner, Location location);

    CompletableFuture<Integer> deleteHome(Player player, String homeName);
    CompletableFuture<Boolean> hasHomeWithSpecificName(Player player, String homeName);

    CompletableFuture<Set<Home>> getHomes();

    CompletableFuture<Set<Home>> getHomes(Player player);
}
