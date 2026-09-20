package com.eternalcode.core.ip;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerIpService {

    void recordLogin(UUID targetUuid, String targetName, String ip);

    Optional<String> findLastKnownIp(UUID targetUuid);

    List<AltAccount> findAltAccounts(UUID targetUuid);
}
