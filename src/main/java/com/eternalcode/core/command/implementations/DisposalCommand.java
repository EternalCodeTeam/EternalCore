/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


@FunnyComponent
public final class DisposalCommand {
    private final MessagesConfiguration messagesConfiguration;

    public DisposalCommand(ConfigurationManager configurationManager) {
        this.messagesConfiguration = configurationManager.getMessagesConfiguration();
    }

    @FunnyCommand(
        name = "disposal",
        aliases = {"smietnik"},
        permission = "eternalcode.command.disposal",
        usage = "&cPoprawne użycie &7/disposal <player>",
        acceptsExceeded = true
    )
    public void execute(Player player) {
        player.openInventory(Bukkit.createInventory(null, 54, (ChatUtils.component(messagesConfiguration.disposalTitle))));
        player.sendMessage(ChatUtils.color(messagesConfiguration.disposalGuiOpenMessage));
    }
}
