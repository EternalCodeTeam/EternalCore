/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.resources.ValidationException;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.command.CommandSender;

import static com.eternalcode.core.command.Valid.when;

@FunnyComponent
public final class EternalCoreCommand {
    private final ConfigurationManager configurationManager;

    public EternalCoreCommand(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    @FunnyCommand(
        name = "eternalcore",
        aliases = {"eternal", "eternalmc", "core", "eternalplugin", "eternaltools", "eternalessentials"},
        usage = "&8» &cPoprawne użycie &7/eternalcore <reload/version/authors>",
        permission = "eternalcore.command.eternalcore"
    )
    public void execute(CommandSender sender, String[] args, CommandInfo commandInfo) {
        when(args.length < 2, commandInfo.getUsageMessage());
        MessagesConfiguration config = configurationManager.getMessagesConfiguration();
        // Dont remove this, this command will have more arguments, so switch will be useful for us
        switch (args[0].toLowerCase()) {
            case "reload" -> {
                configurationManager.loadConfigs();
                sender.sendMessage(config.successfullyReloaded);
            }
            default -> throw new ValidationException(commandInfo.getUsageMessage());
        }
    }
}
