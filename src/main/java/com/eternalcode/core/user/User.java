/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.user;

import lombok.Getter;

import java.util.UUID;

public class User {

    @Getter private final String name;
    @Getter private final UUID uuid;

    User(UUID uuid, String name) {
        this.name = name;
        this.uuid = uuid;
    }
}
