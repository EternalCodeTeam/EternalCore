package com.eternalcode.core.feature.ignore;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IgnoreService {

    CompletableFuture<Boolean> isIgnored(UUID requester, UUID target);

    CompletableFuture<Boolean> ignore(UUID requester, UUID target);

    CompletableFuture<Boolean> ignoreAll(UUID requester);

    CompletableFuture<Boolean> unIgnore(UUID requester, UUID target);

    CompletableFuture<Boolean> unIgnoreAll(UUID requester);

}
