package com.eternalcode.core.user.database;

import com.eternalcode.core.user.UserImpl;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserRepository {

    CompletableFuture<Optional<UserImpl>> getUser(UUID uniqueId);

    CompletableFuture<Optional<UserImpl>> getUser(String name);

    CompletableFuture<Void> saveUser(UserImpl user);

}
