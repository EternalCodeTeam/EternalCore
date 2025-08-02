package com.eternalcode.core.configuration.migration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class M0001_MoveRandomPlayerTeleportToOwnSection extends NamedMigration {

    public M0001_MoveRandomPlayerTeleportToOwnSection() {
        super(
            "0001-move-random-player-teleport-to-own-section",

            move("teleport.randomPlayerNotFound", "teleportToRandomPlayer.randomPlayerNotFound"),
            move("teleport.teleportedToRandomPlayer", "teleportToRandomPlayer.teleportedToRandomPlayer")
        );
    }
}
