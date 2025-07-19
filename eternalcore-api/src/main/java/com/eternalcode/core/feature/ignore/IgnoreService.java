package com.eternalcode.core.feature.ignore;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IgnoreService {

    CompletableFuture<Boolean> isIgnored(UUID requester, UUID target);

    void ignore(UUID requester, UUID target);

    void ignoreAll(UUID requester);

    void unIgnore(UUID requester, UUID target);

    void unIgnoreAll(UUID requester);

}
