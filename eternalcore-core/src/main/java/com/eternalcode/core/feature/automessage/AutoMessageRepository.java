package com.eternalcode.core.feature.automessage;

import java.util.concurrent.CompletableFuture;
import panda.std.reactive.Completable;

import java.util.Set;
import java.util.UUID;

interface AutoMessageRepository {

    CompletableFuture<Set<UUID>> findReceivers(Set<UUID> onlineUniqueIds);

    // for nearby feafure like placeholders etc
    CompletableFuture<Boolean> isReceiving(UUID uniqueId);

    CompletableFuture<Boolean> switchReceiving(UUID uniqueId);

}
