/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.command.argument.PlayerArgOrSender;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;

@Section(route = "anvil", aliases = { "kowadlo", "kowadło" })
@Permission("eternalcore.command.anvil")
public class AnvilCommand {

    @Execute
    public void execute(@Arg(0) @Handler(PlayerArgOrSender.class) Player playerOrSender) {
        playerOrSender.openAnvil(null, true);
    }
}
