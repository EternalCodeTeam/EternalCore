package com.eternalcode.core.command.implementation.inventory;


import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.entity.Player;

@Section(route = "workbench")
@Permission("eternalcore.workbench")
public class WorkbenchCommand {

    @Execute
    public void execute(@Arg @By("or_sender") Player player) {
        player.openWorkbench(null, true);
    }

}

