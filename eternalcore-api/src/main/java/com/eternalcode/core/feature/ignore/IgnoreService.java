package com.eternalcode.core.feature.ignore;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IgnoreService {

    CompletableFuture<Boolean> isIgnored(UUID by, UUID target);

    CompletableFuture<Boolean> ignore(UUID by, UUID target);

    CompletableFuture<Boolean> ignoreAll(UUID by);

    CompletableFuture<Boolean> unIgnore(UUID by, UUID target);

    CompletableFuture<Boolean> unIgnoreAll(UUID by);

}
