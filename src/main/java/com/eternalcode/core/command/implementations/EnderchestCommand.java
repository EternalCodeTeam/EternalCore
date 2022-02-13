/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;

@Section(route = "enderchest", aliases = { "ec" })
@Permission("eternalcore.command.enderchest")
public class EnderchestCommand {

    @Execute
    public void execute(Player player) {
        player.openInventory(player.getEnderChest());
    }
}
