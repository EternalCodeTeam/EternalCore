/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.ChatUtils;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@FunnyComponent
public final class EternalCoreCommand {
    private final ConfigurationManager configurationManager;

    public EternalCoreCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "eternalcore",
        aliases = {"eternal", "eternalmc", "core", "eternalplugin", "eternaltools", "eternalessentials"},
        usage = "&8» &cPoprawne użycie &7/eternalcore <reload>",
        permission = "eternalcore.command.eternalcore"
    )

    public void execute(Player player, String[] args) {
        MessagesConfiguration config = configurationManager.getMessagesConfiguration();

        switch (args[0]) {
            case "reload" -> {
                configurationManager.loadConfigs();
                player.sendMessage(config.successfullyReloaded);
                Bukkit.getLogger().info(ChatUtils.color("Configs has ben successfuly reloaded!"));
            }
        }
    }
}
