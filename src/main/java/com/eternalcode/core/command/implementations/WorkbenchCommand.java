/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;

@FunnyComponent
public final class WorkbenchCommand {
    @FunnyCommand(
        name = "workbench",
        permission = "eternalcore.command.workbench",
        usage = "&8» &cPoprawne użycie &7/workbench",
        acceptsExceeded = true
    )
    public void execute(Player player) {
        player.openWorkbench(null, true);
    }
}

