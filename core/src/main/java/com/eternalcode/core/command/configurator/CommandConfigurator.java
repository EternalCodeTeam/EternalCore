package com.eternalcode.core.command.configurator;

import com.eternalcode.core.command.configurator.config.Command;
import com.eternalcode.core.command.configurator.config.CommandConfiguration;
import com.eternalcode.core.command.configurator.config.SubCommand;
import dev.rollczi.litecommands.factory.CommandEditor;

public class CommandConfigurator implements CommandEditor {

    private final CommandConfiguration commandConfiguration;

    public CommandConfigurator(CommandConfiguration commandConfiguration) {
        this.commandConfiguration = commandConfiguration;
    }

    @Override
    public State edit(State state) {
        Command command = this.commandConfiguration.commands.get(state.getName());

        if (command == null) {
            return state;
        }

        for (String child : command.subCommands().keySet()) {
            SubCommand subCommand = command.subCommands().get(child);

            state = state.editChild(child, editor -> editor.name(subCommand.name())
                .aliases(subCommand.aliases(), true)
                .permission(subCommand.permissions(), true)
                .cancel(subCommand.isDisabled()));
        }

        return state
            .name(command.name())
            .aliases(command.aliases(), true)
            .permission(command.permissions(), true)
            .cancel(command.isDisabled());
    }

}
