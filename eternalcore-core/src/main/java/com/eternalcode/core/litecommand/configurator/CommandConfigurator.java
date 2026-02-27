package com.eternalcode.core.litecommand.configurator;


import com.eternalcode.core.litecommand.configurator.config.Command;
import com.eternalcode.core.litecommand.configurator.config.CommandConfiguration;
import com.eternalcode.core.litecommand.configurator.config.SubCommand;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteCommandEditor;
import dev.rollczi.litecommands.command.builder.CommandBuilder;
import dev.rollczi.litecommands.editor.Editor;
import dev.rollczi.litecommands.meta.Meta;
import dev.rollczi.litecommands.permission.PermissionSet;
import java.util.List;
import java.util.function.UnaryOperator;
import org.bukkit.command.CommandSender;

@LiteCommandEditor
class CommandConfigurator implements Editor<CommandSender> {

    private final CommandConfiguration commandConfiguration;

    @Inject
    CommandConfigurator(CommandConfiguration commandConfiguration) {
        this.commandConfiguration = commandConfiguration;
    }

    @Override
    public CommandBuilder<CommandSender> edit(CommandBuilder<CommandSender> context) {
        Command command = this.commandConfiguration.commands.get(context.name());

        if (command == null) {
            return context;
        }

        for (String child : command.subCommands().keySet()) {
            SubCommand subCommand = command.subCommands().get(child);

            context = context.editChild(child, editor -> editor.name(subCommand.name())
                .aliases(subCommand.aliases())
                .applyMeta(editPermissions(command.permissions()))
                .enabled(subCommand.isEnabled())
            );
        }

        return context
            .name(command.name())
            .aliases(command.aliases())
            .applyMeta(editPermissions(command.permissions()))
            .enabled(command.isEnabled());
    }

    private static UnaryOperator<Meta> editPermissions(List<String> permissions) {
        return meta -> meta.listEditor(Meta.PERMISSIONS)
            .clear()
            .add(new PermissionSet(permissions))
            .apply();
    }

}
