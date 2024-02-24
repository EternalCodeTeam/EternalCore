package com.eternalcode.core.feature.home;

import com.eternalcode.core.user.User;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

interface HomeRepository {

    CompletableFuture<Optional<Home>> getHome(UUID uuid);

    CompletableFuture<Optional<Home>> getHome(User user, String name);

    CompletableFuture<Void> saveHome(Home home);

    CompletableFuture<Integer> deleteHome(UUID uuid);

    CompletableFuture<Integer> deleteHome(User user, String name);

    CompletableFuture<Set<Home>> getHomes();

    CompletableFuture<Set<Home>> getHomes(User user);
}
