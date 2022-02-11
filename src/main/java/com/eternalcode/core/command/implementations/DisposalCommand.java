/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


@Section(route = "disposal", aliases = { "smietnik" })
@Permission("eternalcore.command.disposal")
public class DisposalCommand {

    private final MessagesConfiguration message;

    public DisposalCommand(MessagesConfiguration message) {
        this.message = message;
    }

    @Execute
    public void execute(Player player) {
        player.openInventory(Bukkit.createInventory(null, 54, ChatUtils.component(this.message.otherMessages.disposalTitle)));
        player.sendMessage(ChatUtils.color(this.message.otherMessages.disposalOpenMessage));
    }
}
