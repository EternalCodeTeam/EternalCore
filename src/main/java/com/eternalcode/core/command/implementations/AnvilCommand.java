/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.chat.ChatUtils;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;

@FunnyComponent
public final class AnvilCommand {

    private final ConfigurationManager configurationManager;

    public AnvilCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "anvil",
        aliases = {"kowadlo", "kowadło"},
        permission = "eternalcore.command.anvil",
        usage = "&8» &cPoprawne użycie &7/anvil <player>",
        acceptsExceeded = true
    )

    public void execute(Player player) {
        player.openAnvil(null, true);
        MessagesConfiguration config = configurationManager.getMessagesConfiguration();
        player.sendMessage(ChatUtils.color(config.anvilGuiOpenMessage));
    }
}

