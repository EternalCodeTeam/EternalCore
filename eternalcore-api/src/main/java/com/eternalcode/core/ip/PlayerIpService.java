package com.eternalcode.core.ip;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PlayerIpService {

    CompletableFuture<Void> recordLogin(UUID targetUuid, String targetName, String ip);

    CompletableFuture<Optional<String>> findLastKnownIp(UUID targetUuid);

    CompletableFuture<List<AltAccount>> findAltAccounts(UUID targetUuid);
}
