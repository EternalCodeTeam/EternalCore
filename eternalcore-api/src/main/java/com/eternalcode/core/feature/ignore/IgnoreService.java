package com.eternalcode.core.feature.ignore;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IgnoreService {

    CompletableFuture<Boolean> isIgnored(UUID requester, UUID target);

    CompletableFuture<IgnoreResult> ignore(UUID requester, UUID target);

    CompletableFuture<IgnoreResult> ignoreAll(UUID requester);

    CompletableFuture<IgnoreResult> unIgnore(UUID requester, UUID target);

    CompletableFuture<IgnoreResult> unIgnoreAll(UUID requester);

}
