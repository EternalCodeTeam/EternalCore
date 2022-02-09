/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.user;

import java.util.UUID;

public class User {

    private final String name;
    private final UUID uuid;

    User(UUID uuid, String name) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }
}
