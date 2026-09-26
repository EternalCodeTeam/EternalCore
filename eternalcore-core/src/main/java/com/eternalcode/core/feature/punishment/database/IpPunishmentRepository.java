package com.eternalcode.core.feature.punishment.database;

import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.ip.IpPunishment;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IpPunishmentRepository {

    CompletableFuture<Void> save(IpPunishment ipPunishment);

    CompletableFuture<Boolean> revoke(UUID id, PunishmentTarget revokedBy, Instant revokedAt);

    CompletableFuture<Optional<IpPunishment>> findActiveByIp(String ip);

    CompletableFuture<List<IpPunishment>> findAllActive();
}
