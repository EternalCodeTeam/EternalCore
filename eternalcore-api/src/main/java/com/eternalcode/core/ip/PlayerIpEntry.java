package com.eternalcode.core.ip;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class PlayerIpEntry {

    private final UUID id;
    private final UUID targetUuid;
    private final String targetName;
    private final String ip;
    private final Instant firstSeen;
    private final Instant lastSeen;

    public PlayerIpEntry(UUID id, UUID targetUuid, String targetName, String ip, Instant firstSeen, Instant lastSeen) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.targetUuid = Objects.requireNonNull(targetUuid, "targetUuid cannot be null");
        this.targetName = Objects.requireNonNull(targetName, "targetName cannot be null");
        this.ip = Objects.requireNonNull(ip, "ip cannot be null");
        this.firstSeen = Objects.requireNonNull(firstSeen, "firstSeen cannot be null");
        this.lastSeen = Objects.requireNonNull(lastSeen, "lastSeen cannot be null");

        if (this.ip.isBlank()) {
            throw new IllegalArgumentException("ip cannot be blank");
        }
        if (this.lastSeen.isBefore(this.firstSeen)) {
            throw new IllegalArgumentException("lastSeen cannot be before firstSeen");
        }
    }

    public UUID id() {
        return this.id;
    }

    public UUID targetUuid() {
        return this.targetUuid;
    }

    public String targetName() {
        return this.targetName;
    }

    public String ip() {
        return this.ip;
    }

    public Instant firstSeen() {
        return this.firstSeen;
    }

    public Instant lastSeen() {
        return this.lastSeen;
    }
}
