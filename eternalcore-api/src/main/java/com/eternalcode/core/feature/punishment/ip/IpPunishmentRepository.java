package com.eternalcode.core.feature.punishment.ip;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IpPunishmentRepository {

    CompletableFuture<Void> save(IpPunishment ipPunishment);

    CompletableFuture<Void> deactivate(UUID id);

    CompletableFuture<Optional<IpPunishment>> findActiveByIp(String ip);

    CompletableFuture<List<IpPunishment>> findAllActive();
}
