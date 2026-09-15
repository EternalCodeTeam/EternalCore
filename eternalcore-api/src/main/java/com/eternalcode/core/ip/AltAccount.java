package com.eternalcode.core.ip;

import java.util.Objects;
import java.util.UUID;

public record AltAccount(UUID uuid, String name) {

    public AltAccount {
        Objects.requireNonNull(uuid, "uuid cannot be null");
        Objects.requireNonNull(name, "name cannot be null");
    }
}
