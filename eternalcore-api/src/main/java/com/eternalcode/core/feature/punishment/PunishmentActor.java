package com.eternalcode.core.feature.punishment;

import java.util.UUID;

/**
 * Who issued or revoked a punishment: a player or the console.
 */
public sealed interface PunishmentActor {

    String name();

    record Player(UUID uuid, String name) implements PunishmentActor {
    }

    record Console(String name) implements PunishmentActor {
    }
}
