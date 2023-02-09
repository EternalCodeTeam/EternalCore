package com.eternalcode.core.feature.essentials.container;


import com.eternalcode.containers.AdditionalContainerPaper;
import com.eternalcode.containers.AdditionalContainerType;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "stonecutter")
@Permission("eternalcore.workbench")
public class StonecutterCommand {

    @Execute
    void executeSelf(Player player) {
        AdditionalContainerPaper.openAdditionalContainer(player, AdditionalContainerType.STONE_CUTTER);
    }

    @Execute
    void execute(@Arg Player target) {
        AdditionalContainerPaper.openAdditionalContainer(target, AdditionalContainerType.STONE_CUTTER);
    }

}

