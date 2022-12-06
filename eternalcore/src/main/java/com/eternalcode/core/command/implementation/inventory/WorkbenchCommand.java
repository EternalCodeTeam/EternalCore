package com.eternalcode.core.command.implementation.inventory;


import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.entity.Player;

@Route(name = "workbench")
@Permission("eternalcore.workbench")
public class WorkbenchCommand {

    @Execute
    void execute(@Arg Player player) {
        player.openWorkbench(null, true);
    }

    @Execute
    void executeSelf(Player sender) {
        sender.openWorkbench(null, true);
    }

}

