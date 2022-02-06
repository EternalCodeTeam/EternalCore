/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.std.Option;

@Section(route = "clear")
@Permission("eternalcore.command.clear")
public class ClearCommand {

    private final MessagesConfiguration message;

    public ClearCommand(MessagesConfiguration message) {
        this.message = message;
    }

    @Execute
    public void execute(CommandSender sender, String[] args) {
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0]))
            .orElse(Option.of(sender).is(Player.class))
            .peek(player -> {
                player.getInventory().setArmorContents(new ItemStack[0]);
                player.getInventory().clear();
                player.sendMessage(ChatUtils.color(message.messagesSection.inventoryCleared));
            }).onEmpty(() -> sender.sendMessage(ChatUtils.color(message.messagesSection.offlinePlayer)));
    }
}
