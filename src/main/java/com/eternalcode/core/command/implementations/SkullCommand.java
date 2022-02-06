/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.builders.ItemBuilder;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Section(route = "skull", aliases = {"głowa, glowa"})
@Permission("eternalcore.command.skull")
@UsageMessage("&8» &cPoprawne użycie &7/skull <player>")
public class SkullCommand {

    @Execute @MinArgs(1)
    public void execute(EternalCore eternalCore, Player player, String[] args) {
        Bukkit.getScheduler().runTaskAsynchronously(eternalCore, () -> {
            ItemStack item = new ItemBuilder(Material.PLAYER_HEAD)
                .displayName(args[0])
                .skullOwner(args[0])
                .build();

            player.getInventory().addItem(item);
        });
    }
}
