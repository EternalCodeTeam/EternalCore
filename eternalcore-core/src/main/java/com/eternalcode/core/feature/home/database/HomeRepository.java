package com.eternalcode.core.feature.home.database;

import com.eternalcode.core.feature.home.Home;
import com.eternalcode.core.user.User;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface HomeRepository {

    CompletableFuture<Optional<Home>> getHome(UUID playerUniqueId);

    CompletableFuture<Optional<Home>> getHome(User user, String homeName);

    CompletableFuture<Optional<Home>> getHome(UUID playerUniqueId, String homeName);

    CompletableFuture<Void> saveHome(Home home);

    CompletableFuture<Integer> deleteHome(UUID playerUniqueId);

    CompletableFuture<Integer> deleteHome(User user, String homeName);

    CompletableFuture<Integer> deleteHome(UUID playerUniqueId, String homeName);

    CompletableFuture<Set<Home>> getHomes();

    CompletableFuture<Set<Home>> getHomes(User user);
    CompletableFuture<Set<Home>> getHomes(UUID playerUniqueId);
}
