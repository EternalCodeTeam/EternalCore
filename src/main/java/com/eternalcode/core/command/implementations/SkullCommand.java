/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.utils.ItemBuilder;
import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.eternalcode.core.command.Valid.when;

@FunnyComponent
public final class SkullCommand {

    @FunnyCommand(
        name = "skull",
        permission = "eternalcore.command.skull",
        usage = "&8» &cPoprawne użycie &7/skull <player>",
        playerOnly = true,
        acceptsExceeded = true
    )
    public void execute(EternalCore plugin, Player player, String[] args, CommandInfo commandInfo) {
        when(args.length != 1, commandInfo.getUsageMessage());

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            ItemStack item = new ItemBuilder(Material.PLAYER_HEAD)
                .displayName(args[0])
                .skullOwner(args[0])
                .build();

            player.getInventory().addItem(item);
        });
    }
}
