package com.eternalcode.core.configuration.migrations;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class Migration_0034_Move_chat_settings_messages_to_dedicated_section extends NamedMigration {
    Migration_0034_Move_chat_settings_messages_to_dedicated_section() {
        super(
            "Move chat settings messages to dedicated section",

            move("chat.disabled", "chat.chatDisabled"),
            move("chat.enabled", "chat.chatEnabled"),
            move("chat.cleared", "chat.chatCleared"),

            move("chat.alreadyDisabled", "chat.chatAlreadyDisabled"),
            move("chat.alreadyEnabled", "chat.chatAlreadyEnabled"),

            move("chat.slowModeOff", "chat.slowModeDisabled"),
            move("chat.slowMode", "chat.slowModeNotReady"),

            move("chat.disabledChatInfo", "chat.chatDisabledInfo")
        );
    }
}
