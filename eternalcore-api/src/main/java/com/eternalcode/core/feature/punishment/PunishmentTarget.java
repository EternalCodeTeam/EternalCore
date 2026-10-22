package com.eternalcode.core.feature.punishment;

import java.util.UUID;

/**
 * Player account that is being punished. Operators are described by {@link PunishmentActor}.
 */
public record PunishmentTarget(UUID uuid, String name) {
}
