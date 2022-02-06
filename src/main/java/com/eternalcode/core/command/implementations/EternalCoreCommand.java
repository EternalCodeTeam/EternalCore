/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.utils.ChatUtils;
import com.eternalcode.core.configuration.MessagesConfiguration;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.resources.ValidationException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static com.eternalcode.core.command.Valid.when;

@Section(route = "eternalcore", aliases = {"eternal", "eternalmc", "core", "eternalplugin", "eternaltools", "eternalessentials"})
@UsageMessage("&8» &cPoprawne użycie &7/eternalcore <reload>")
@Permission("eternalcore.command.eternalcore")
public class EternalCoreCommand {
    private final MessagesConfiguration message;
    private final ConfigurationManager configurationManager;

    public EternalCoreCommand(MessagesConfiguration message, ConfigurationManager configurationManager) {
        this.message = message;
        this.configurationManager = configurationManager;
    }


    @Execute
    @MinArgs(1)
    public void execute(Player player, String[] args, CommandInfo commandInfo) {
        when(args.length < 1, commandInfo.getUsageMessage());

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                configurationManager.loadAndRenderConfigs();
                player.sendMessage(message.messagesSection.successfullyReloaded);
                Bukkit.getLogger().info(ChatUtils.color("Configs has ben successfuly reloaded!"));
            }
            default -> throw new ValidationException(commandInfo.getUsageMessage());
        }
    }
}
