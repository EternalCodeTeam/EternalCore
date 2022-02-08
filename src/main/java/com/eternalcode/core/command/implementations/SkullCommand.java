/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.builders.ItemBuilder;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import panda.utilities.StringUtils;

@Section(route = "skull", aliases = {"głowa, glowa"})
@Permission("eternalcore.command.skull")
@UsageMessage("&8» &cPoprawne użycie &7/skull <player>")
public class SkullCommand {

    @Execute
    @MinArgs(1)
    public void execute(EternalCore eternalCore, Player player, String[] args, MessagesConfiguration messages) {
        Bukkit.getScheduler().runTaskAsynchronously(eternalCore, () -> {
            ItemStack item = new ItemBuilder(Material.PLAYER_HEAD)
                .displayName(args[0])
                .skullOwner(args[0])
                .build();

            player.getInventory().addItem(item);
            player.sendMessage(ChatUtils.color(StringUtils.replace(messages.messagesSection.skullMessage, "{NICK}", args[0])));
        });
    }
}
