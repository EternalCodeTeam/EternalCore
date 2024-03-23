package com.eternalcode.core.feature.ignore;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IgnoreService {

    CompletableFuture<Boolean> isIgnored(UUID by, UUID target);

    CompletableFuture<Void> ignore(UUID by, UUID target);

    CompletableFuture<Void> ignoreAll(UUID by);

    CompletableFuture<Void> unIgnore(UUID by, UUID target);

    CompletableFuture<Void> unIgnoreAll(UUID by);

    CompletableFuture<Void> purgeAll();

}
