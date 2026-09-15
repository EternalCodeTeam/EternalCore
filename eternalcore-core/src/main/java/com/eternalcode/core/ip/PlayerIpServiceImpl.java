package com.eternalcode.core.ip;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
class PlayerIpServiceImpl implements PlayerIpService {

    private final PlayerIpRepository playerIpRepository;

    @Inject
    PlayerIpServiceImpl(PlayerIpRepository playerIpRepository) {
        this.playerIpRepository = playerIpRepository;
    }

    @Override
    public CompletableFuture<Void> recordLogin(UUID targetUuid, String targetName, String ip) {
        Objects.requireNonNull(targetUuid, "targetUuid cannot be null");
        Objects.requireNonNull(targetName, "targetName cannot be null");
        Objects.requireNonNull(ip, "ip cannot be null");

        return this.playerIpRepository.recordLogin(targetUuid, targetName, ip);
    }

    @Override
    public CompletableFuture<Optional<String>> findLastKnownIp(UUID targetUuid) {
        Objects.requireNonNull(targetUuid, "targetUuid cannot be null");

        return this.playerIpRepository.findLatest(targetUuid).thenApply(entry -> entry.map(PlayerIpEntry::ip));
    }

    @Override
    public CompletableFuture<List<AltAccount>> findAltAccounts(UUID targetUuid) {
        Objects.requireNonNull(targetUuid, "targetUuid cannot be null");

        return this.playerIpRepository.findLatest(targetUuid)
            .thenCompose(latest -> latest
                .map(entry -> this.playerIpRepository.findAllByIp(entry.ip()))
                .orElse(CompletableFuture.completedFuture(List.of())))
            .thenApply(entries -> entries.stream()
                .filter(entry -> !entry.targetUuid().equals(targetUuid))
                .map(entry -> new AltAccount(entry.targetUuid(), entry.targetName()))
            .distinct()
            .collect(Collectors.toList()));
    }
}
