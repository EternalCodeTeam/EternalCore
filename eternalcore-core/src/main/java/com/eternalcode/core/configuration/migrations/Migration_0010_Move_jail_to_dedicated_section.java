package com.eternalcode.core.configuration.migrations;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

public class Migration_0010_Move_jail_to_dedicated_section extends NamedMigration {

    Migration_0010_Move_jail_to_dedicated_section() {
        super(
            "Move jail to dedicated section",

            move("jailSection.jailLocationSet", "jail.locationSet"),
            move("jailSection.jailLocationRemove", "jail.locationRemove"),
            move("jailSection.jailLocationNotSet", "jail.locationNotSet"),
            move("jailSection.jailLocationOverride", "jail.locationOverride"),

            move("jailSection.jailDetainBroadcast", "jail.detainBroadcast"),
            move("jailSection.jailDetainPrivate", "jail.detainPrivate"),
            move("jailSection.jailDetainCountdown", "jail.detainCountdown"),
            move("jailSection.jailDetainOverride", "jail.detainOverride"),
            move("jailSection.jailDetainAdmin", "jail.detainAdmin"),

            move("jailSection.jailReleaseBroadcast", "jail.releaseBroadcast"),
            move("jailSection.jailReleasePrivate", "jail.releasePrivate"),
            move("jailSection.jailReleaseAll", "jail.releaseAll"),
            move("jailSection.jailReleaseNoPlayers", "jail.releaseNoPlayers"),
            move("jailSection.jailIsNotPrisoner", "jail.isNotPrisoner"),

            move("jailSection.jailListHeader", "jail.listHeader"),
            move("jailSection.jailListEmpty", "jail.listEmpty"),
            move("jailSection.jailListPlayerEntry", "jail.listPlayerEntry"),

            move("jailSection.jailCannotUseCommand", "jail.cannotUseCommand")
        );
    }
}
