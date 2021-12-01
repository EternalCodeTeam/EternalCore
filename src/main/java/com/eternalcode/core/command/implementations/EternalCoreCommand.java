package com.eternalcode.core.command.implementations;

import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.resources.ValidationException;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.command.CommandSender;

import static com.eternalcode.core.command.Valid.when;

@FunnyComponent
public final class EternalCoreCommand {
    @FunnyCommand(
        name = "eternalcore",
        aliases = {"eternal", "eternalmc", "core", "eternalplugin", "eternaltools", "eternalessentials"},
        usage = "&8» &cPoprawne użycie &7/eternalcore <reload/version/authors>",
        permission = "eternalcore.command.eternalcore"
    )
    public void execute(CommandSender sender, String[] args, CommandInfo commandInfo) {
        when(args.length < 2, commandInfo.getUsageMessage());
        switch (args[0].toLowerCase()) {
            case "reload" -> {
            }
            case "version" -> {
            }
            case "authors" -> {
            }
            default -> throw new ValidationException(commandInfo.getUsageMessage());
        }
    }
}
