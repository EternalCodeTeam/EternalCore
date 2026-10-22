package com.eternalcode.core.ip;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Methods block until the database answers - call them only from an asynchronous context.
 */
public interface PlayerIpRepository {

    void recordLogin(UUID targetUuid, String targetName, String ip);

    Optional<PlayerIpEntry> findLatest(UUID targetUuid);

    List<PlayerIpEntry> findAllByTarget(UUID targetUuid);

    List<PlayerIpEntry> findAllByIp(String ip);

    void deleteOlderThan(Instant threshold);
}
