package com.eternalcode.core.command.implementations;

import com.eternalcode.core.command.argument.PlayerArgOrSender;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;

@Section(route = "stonecutter")
@Permission("eternalcore.command.workbench")
public class StonecutterCommand {

    @Execute
    public void execute(@Arg(0) @Handler(PlayerArgOrSender.class) Player player) {
        player.openStonecutter(null, true);
    }

}

