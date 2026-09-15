package com.eternalcode.core.ip;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PlayerIpRepository {

    CompletableFuture<Void> recordLogin(UUID targetUuid, String targetName, String ip);

    CompletableFuture<Optional<PlayerIpEntry>> findLatest(UUID targetUuid);

    CompletableFuture<List<PlayerIpEntry>> findAllByTarget(UUID targetUuid);

    CompletableFuture<List<PlayerIpEntry>> findAllByIp(String ip);

    CompletableFuture<Void> deleteOlderThan(Instant threshold);
}
