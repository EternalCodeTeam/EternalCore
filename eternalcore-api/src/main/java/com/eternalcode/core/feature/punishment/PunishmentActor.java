package com.eternalcode.core.feature.punishment;

import java.util.UUID;

/**
 * Who issued or revoked a punishment: a player or the console.
 */
public record PunishmentActor(UUID uuid, String name) {

    static UUID CONSOLE_UUID = UUID.nameUUIDFromBytes("**CONSOLE**".getBytes());

    public static PunishmentActor console(String name) {
        return new PunishmentActor(CONSOLE_UUID, name);
    }

    public boolean isConsole() {
        return uuid() == CONSOLE_UUID;
    }

}
