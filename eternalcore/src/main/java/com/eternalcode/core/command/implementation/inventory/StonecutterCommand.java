package com.eternalcode.core.command.implementation.inventory;


import com.eteranlcode.containers.AdditionalContainerPaper;
import com.eteranlcode.containers.AdditionalContainerType;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

@Section(route = "stonecutter")
@Permission("eternalcore.workbench")
public class StonecutterCommand {

    private final Logger logger;

    public StonecutterCommand(Logger logger) {
        this.logger = logger;
    }

    @Execute
    void execute(@Arg @By("or_sender") Player player) {
        AdditionalContainerPaper.openAdditionalContainer(player, this.logger, AdditionalContainerType.STONE_CUTTER);
    }
}

