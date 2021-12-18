/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;

@FunnyComponent
public final class CartographyTableCommand {

    private final ConfigurationManager configurationManager;

    public CartographyTableCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "cartopgraphytable",
        permission = "eternalcore.command.cartopgraphytable",
        usage = "&8» &cPoprawne użycie &7/cartopgraphytable <player>",
        acceptsExceeded = true
    )
    public void execute(Player player) {
        MessagesConfiguration config = configurationManager.getMessagesConfiguration();
        player.openCartographyTable(null, true);
        player.sendMessage(ChatUtils.color(config.cartographyTableGuiOpenMessage));
    }
}
