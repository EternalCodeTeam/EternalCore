/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;

@Section(route = "stonecutter")
@Permission("eternalcore.command.workbench")
public class StonecutterCommand {

    @Execute
    public void execute(Player player) {
        player.openStonecutter(null, true);
    }
}

