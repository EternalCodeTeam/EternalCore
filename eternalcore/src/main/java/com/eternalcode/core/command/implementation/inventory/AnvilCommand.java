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

@Section(route = "anvil", aliases = { "kowadlo", "kowadło" })
@Permission("eternalcore.anvil")
public class AnvilCommand {

    private final Plugin plugin;

    public AnvilCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Execute
    void execute(@Arg @By("or_sender") Player player) {
        AdditionalContainerPaper.openAdditionalContainer(player, this.plugin.getLogger(), AdditionalContainerType.ANVIL);
    }
}
