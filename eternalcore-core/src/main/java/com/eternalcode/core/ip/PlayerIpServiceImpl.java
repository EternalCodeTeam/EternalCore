package com.eternalcode.core.ip;

import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class PlayerIpServiceImpl implements PlayerIpService {

    private final PlayerIpRepository playerIpRepository;

    @Inject
    PlayerIpServiceImpl(PlayerIpRepository playerIpRepository) {
        this.playerIpRepository = playerIpRepository;
    }

    @Override
    public void recordLogin(UUID targetUuid, String targetName, String ip) {
        this.playerIpRepository.recordLogin(targetUuid, targetName, ip);
    }

    @Override
    public String findLastKnownIp(UUID targetUuid) {
        return this.playerIpRepository.findLatest(targetUuid).map(PlayerIpEntry::ip).orElse(null);
    }

    @Override
    public List<AltAccount> findAltAccounts(UUID targetUuid) {
        Optional<PlayerIpEntry> latest = this.playerIpRepository.findLatest(targetUuid);

        List<PlayerIpEntry> sameIpEntries = latest
            .map(entry -> this.playerIpRepository.findAllByIp(entry.ip()))
            .orElse(List.of());

        return sameIpEntries.stream()
            .filter(entry -> !entry.targetUuid().equals(targetUuid))
            .map(entry -> new AltAccount(entry.targetUuid(), entry.targetName()))
            .distinct()
            .collect(Collectors.toList());
    }
}
