/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.entity.Player;

@FunnyComponent
public final class StonecutterCommand {
    @FunnyCommand(
        name = "stonecutter",
        permission = "eternalcore.command.stonecutter",
        usage = "&8» &cPoprawne użycie &7/stonecutter <player>",
        acceptsExceeded = true
    )

    public void execute(Player player) {
        player.openStonecutter(null, true);
    }
}

