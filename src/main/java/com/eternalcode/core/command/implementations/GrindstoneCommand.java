/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.entity.Player;

@Section(route = "grindstone")
@UsageMessage("&8» &cPoprawne użycie &7/grindstone <player>")
@Permission("eternalcore.command.grindstone")
public class GrindstoneCommand {

    @Execute
    public void execute(Player player) {
        player.openGrindstone(null, true);
    }
}

