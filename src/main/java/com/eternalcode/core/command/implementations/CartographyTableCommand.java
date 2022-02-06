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

@Section(route = "cartopgraphytable")
@Permission("eternalcore.command.cartopgraphytable")
public class CartographyTableCommand {

    private final MessagesConfiguration message;

    public CartographyTableCommand(MessagesConfiguration message) {
        this.message = message;
    }

    @Execute
    public void execute(Player player) {
        player.openCartographyTable(null, true);
        player.sendMessage(ChatUtils.color(message.messagesSection.cartographyTableOpenMessage));
    }
}
