/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;

@Section (route = "stonecutter")
@Permission ("eternalcore.command.workbench")
public class StonecutterCommand {

    private final MessagesConfiguration message;

    public StonecutterCommand (MessagesConfiguration message) {
        this.message = message;
    }

    @Execute
    public void execute (Player player) {
        player.openWorkbench(null, true);
        player.sendMessage(ChatUtils.color(message.messagesSection.stonecutterOpenMessage));
    }
}

