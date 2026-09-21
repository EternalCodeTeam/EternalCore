package com.eternalcode.core.feature.enderchest.database;

import com.eternalcode.core.feature.enderchest.EnderchestWrite;
import com.eternalcode.core.feature.enderchest.PageContents;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface EnderchestRepository {

    CompletableFuture<List<PageContents>> findPages(UUID ownerUniqueId);

    CompletableFuture<Void> savePages(UUID ownerUniqueId, EnderchestWrite write);

    void shutdownWrites(Duration timeout);
}
