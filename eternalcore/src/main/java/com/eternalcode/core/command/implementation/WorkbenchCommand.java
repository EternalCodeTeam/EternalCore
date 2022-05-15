package com.eternalcode.core.command.implementation;


import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;

@Section(route = "workbench")
@Permission("eternalcore.command.workbench")
public class WorkbenchCommand {

    @Execute
    public void execute(@Arg(0) @Handler(PlayerArgOrSender.class) Player player) {
        player.openWorkbench(null, true);
    }

}

