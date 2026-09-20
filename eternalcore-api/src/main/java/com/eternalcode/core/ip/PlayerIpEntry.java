package com.eternalcode.core.ip;

import java.time.Instant;
import java.util.UUID;

public record PlayerIpEntry(
    UUID id,
    UUID targetUuid,
    String targetName,
    String ip,
    Instant firstSeen,
    Instant lastSeen
) {

    public PlayerIpEntry {
        if (ip.isBlank()) {
            throw new IllegalArgumentException("ip cannot be blank");
        }
        if (lastSeen.isBefore(firstSeen)) {
            throw new IllegalArgumentException("lastSeen cannot be before firstSeen");
        }
    }
}
