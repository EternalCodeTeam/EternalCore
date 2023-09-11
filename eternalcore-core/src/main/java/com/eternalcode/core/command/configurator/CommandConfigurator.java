package com.eternalcode.core.command.configurator;

import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.command.configurator.config.Command;
import com.eternalcode.core.command.configurator.config.CommandConfiguration;
import com.eternalcode.core.command.configurator.config.SubCommand;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteCommandEditor;
import dev.rollczi.litecommands.factory.CommandEditor;

@LiteCommandEditor
class CommandConfigurator implements CommandEditor {

    private final CommandConfiguration commandConfiguration;

    @Inject
    CommandConfigurator(CommandConfiguration commandConfiguration) {
        this.commandConfiguration = commandConfiguration;
    }

    @FeatureDocs(
        name = "CommandConfigurator",
        description = "Adds support for command configuration, which allows you to change the name, aliases, permissions and disable the command"
    )

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
                .cancel(subCommand.isCancel()));
        }

        return state
            .name(command.name())
            .aliases(command.aliases(), true)
            .permission(command.permissions(), true)
            .cancel(command.isCancel());
    }

}
