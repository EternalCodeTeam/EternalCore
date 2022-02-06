/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.entity.Player;

@Section(route = "grindstone")
@UsageMessage("&8» &cPoprawne użycie &7/grindstone <player>")
@Permission("eternalcore.command.grindstone")
public class GrindstoneCommand {

    private final MessagesConfiguration message;

    public GrindstoneCommand(MessagesConfiguration message) {
        this.message = message;
    }

    @Execute
    public void execute(Player player) {
        player.openGrindstone(null, true);
        player.sendMessage(ChatUtils.color(message.messagesSection.grindstoneOpenMessage));
    }
}

