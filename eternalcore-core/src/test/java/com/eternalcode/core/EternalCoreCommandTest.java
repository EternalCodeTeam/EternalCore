package com.eternalcode.core;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.Test;

class EternalCoreCommandTest {

    @Test
    void shouldAcceptEveryBukkitCommandSenderForReload() {
        assertThatCode(() -> EternalCoreCommand.class.getDeclaredMethod("reload", CommandSender.class))
            .doesNotThrowAnyException();
    }
}
