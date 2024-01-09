package com.eternalcode.core.bridge.commodore;

import com.eternalcode.core.bridge.litecommand.configurator.config.Command;
import com.eternalcode.core.bridge.litecommand.configurator.config.CommandConfiguration;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.component.Service;
import dev.rollczi.litecommands.command.CommandManager;
import dev.rollczi.litecommands.command.CommandRoute;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;

@Service
public class CommodoreService {
    private final CommandManager<CommandSender> commandManager;
    private final CommandConfiguration commandConfiguration;

    @Inject
    public CommodoreService(CommandManager<CommandSender> commandManager, CommandConfiguration commandConfiguration) {
        this.commandManager = commandManager;
        this.commandConfiguration = commandConfiguration;
    }

    public String findCommandName(String commandName) {
        return Optional.ofNullable(this.commandManager.getRoot().getChild(commandName).orElse(null))
            .map(CommandRoute::getName)
            .orElseGet(() -> Optional.ofNullable(this.commandConfiguration.commands.get(commandName))
                .map(Command::name).orElse(null));
    }

    public List<String> findCommandAliases(String commandName) {
        return Optional.ofNullable(this.commandManager.getRoot().getChild(commandName).orElse(null))
            .map(CommandRoute::getAliases)
            .orElseGet(() -> Optional.ofNullable(this.commandConfiguration.commands.get(commandName))
                .map(Command::aliases).orElse(null));
    }
}
