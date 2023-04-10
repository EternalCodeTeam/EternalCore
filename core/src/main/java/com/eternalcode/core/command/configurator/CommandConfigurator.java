package com.eternalcode.core.command.configurator;

import dev.rollczi.litecommands.factory.CommandEditor;

public class CommandConfigurator implements CommandEditor {

    private final CommandConfiguration commandConfiguration;

    public CommandConfigurator(CommandConfiguration commandConfiguration) {
        this.commandConfiguration = commandConfiguration;
    }

    @Override
    public State edit(State state) {
        CommandConfiguration.Command command = this.commandConfiguration.commands.get(state.getName());

        if (command == null) {
            return state;
        }

        return state
            .name(command.name)
            .aliases(command.aliases, true)
            .permission(command.permissions, true)
            .cancel(command.disable);
    }

}
