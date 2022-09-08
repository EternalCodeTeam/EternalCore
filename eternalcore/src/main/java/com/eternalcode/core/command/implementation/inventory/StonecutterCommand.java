package com.eternalcode.core.command.implementation.inventory;


import com.eteranlcode.containers.AdditionalContainerPaper;
import com.eteranlcode.containers.AdditionalContainerType;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

@Section(route = "stonecutter")
@Permission("eternalcore.workbench")
public class StonecutterCommand {

    private final Plugin plugin;

    public StonecutterCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Execute
    void execute(@Arg @By("or_sender") Player player) {
        Logger logger = this.plugin.getLogger();
        Environment environment = PaperLib.getEnvironment();

        if (!environment.isPaper()) {
            logger.warning("StoneCutter command feature is only available on paper, use paper or other paper 1-17-1.19x forks");
            player.sendMessage(ChatColor.RED + "StoneCutter command feature is not supported on this server. Please contact the server administrator and check console!");

            return;
        }

        AdditionalContainerPaper.openAdditionalContainer(player, AdditionalContainerType.STONECUTTER);
    }
}

