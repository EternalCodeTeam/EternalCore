/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.lang.Messages;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Section(route = "disposal", aliases = { "smietnik" })
@Permission("eternalcore.command.disposal")
public class DisposalCommand {

    private final Messages messages;
    private final Server server;

    public DisposalCommand(Messages messages, Server server) {
        this.messages = messages;
        this.server = server;
    }

    @Execute
    public void execute(Player player) {
        player.openInventory(this.server.createInventory(null, 54, ChatUtils.component(this.messages.other().disposalTitle())));
    }
}
