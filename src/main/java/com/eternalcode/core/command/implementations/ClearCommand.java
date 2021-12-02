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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.std.Option;

@FunnyComponent
public final class ClearCommand {
    private final ConfigurationManager configurationManager;

    public ClearCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "clear",
        permission = "eternalcore.command.clear",
        usage = "&8» &cPoprawne użycie &7/clear <player>",
        acceptsExceeded = true
    )
    public void execute(CommandSender sender, String[] args) {
        MessagesConfiguration config = configurationManager.getMessagesConfiguration();
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0]))
            .orElse(Option.of(sender).is(Player.class))
            .peek(player -> {
                player.getInventory().setArmorContents(new ItemStack[0]);
                player.getInventory().clear();
                player.sendMessage(ChatUtils.color(config.inventoryCleared));
            }).onEmpty(() -> sender.sendMessage(ChatUtils.color(config.offlinePlayer)));
    }
}
