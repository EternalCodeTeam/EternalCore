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

@Section(route = "workbench")
@Permission("eternalcore.command.workbench")
public class WorkbenchCommand {

    private final MessagesConfiguration message;

    public WorkbenchCommand(MessagesConfiguration message) {
        this.message = message;
    }

    @Execute
    public void execute(Player player) {
        player.openWorkbench(null, true);
        player.sendMessage(ChatUtils.color(message.messagesSection.workbenchOpenMessage));
    }
}

