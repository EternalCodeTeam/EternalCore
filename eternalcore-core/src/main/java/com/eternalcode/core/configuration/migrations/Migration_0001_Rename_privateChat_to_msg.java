package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;
import eu.okaeri.configs.migrate.builtin.NamedMigration;

class Migration_0001_Rename_privateChat_to_msg extends NamedMigration {

    Migration_0001_Rename_privateChat_to_msg() {
        super("Rename privateChat to msg",
            move("privateChat.privateMessageYouToTarget", "privateChat.msgYouToTarget"),
            move("privateChat.privateMessageTargetToYou", "privateChat.msgTargetToYou"),
            move("privateChat", "msg")
        );
    }

}
