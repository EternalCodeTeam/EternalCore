package com.eternalcode.core.feature.ignore;

import java.lang.Void;
import java.util.concurrent.CompletableFuture;

import java.util.UUID;

interface IgnoreRepository {

    CompletableFuture<Boolean> isIgnored(UUID by, UUID target);

    CompletableFuture<Void> ignore(UUID by, UUID target);

    CompletableFuture<Void> ignoreAll(UUID by);

    CompletableFuture<Void> unIgnore(UUID by, UUID target);

    CompletableFuture<Void> unIgnoreAll(UUID by);

}
