/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import panda.std.Option;

@Section(route = "enderchest", aliases = { "ec" })
@Permission("eternalcore.command.enderchest")
public class EnderchestCommand {
    private final MessagesConfiguration message;

    public EnderchestCommand(MessagesConfiguration message) {
        this.message = message;
    }

    @Execute
    public void execute(Player player, String[] args) {
        Option.when(args.length == 1, () -> Bukkit.getPlayer(args[0]))
            .peek((other) -> {
                Inventory otherInventory = other.getEnderChest();
                player.openInventory(otherInventory);
                player.sendMessage(ChatUtils.color(message.messagesSection.enderchestOpenPlayerMessage.replace("{PLAYER}", other.getName())));
            }).onEmpty(() -> player.openInventory(player.getEnderChest()));
    }
}
