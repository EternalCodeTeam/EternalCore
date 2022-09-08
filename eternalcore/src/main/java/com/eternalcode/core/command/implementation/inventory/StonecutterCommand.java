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

@Section(route = "stonecutter")
@Permission("eternalcore.workbench")
public class StonecutterCommand {

    private final Plugin plugin;

    public StonecutterCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Execute
    void execute(@Arg @By("or_sender") Player player) {
        AdditionalContainerPaper.openAdditionalContainer(player, this.plugin.getLogger(), AdditionalContainerType.STONE_CUTTER);
    }
}

