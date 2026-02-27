package com.eternalcode.core.litecommand.configurator;


import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.litecommand.configurator.config.Command;
import com.eternalcode.core.litecommand.configurator.config.CommandConfiguration;
import com.eternalcode.core.litecommand.configurator.config.SubCommand;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteCommandEditor;
import dev.rollczi.litecommands.command.builder.CommandBuilder;
import dev.rollczi.litecommands.editor.Editor;
import dev.rollczi.litecommands.meta.Meta;
import dev.rollczi.litecommands.permission.PermissionSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import org.bukkit.command.CommandSender;

@LiteCommandEditor
class CommandConfigurator implements Editor<CommandSender> {

    private final CommandConfiguration commandConfiguration;
    private final ConfigurationManager configurationManager;

    @Inject
    CommandConfigurator(CommandConfiguration commandConfiguration, ConfigurationManager configurationManager) {
        this.commandConfiguration = commandConfiguration;
        this.configurationManager = configurationManager;
    }

    @Override
    public CommandBuilder<CommandSender> edit(CommandBuilder<CommandSender> context) {
        if (context.isRoot() || context.name() == null || context.name().isBlank()) {
            return context;
        }

        boolean changed = this.synchronizeDefaults(context);
        if (changed) {
            this.configurationManager.save(this.commandConfiguration);
        }

        Command command = this.commandConfiguration.commands.get(context.name());

        if (command == null) {
            return context;
        }

        for (Map.Entry<String, SubCommand> childEntry : command.subCommands().entrySet()) {
            String child = childEntry.getKey();
            SubCommand subCommand = childEntry.getValue();

            if (context.getChild(child).isEmpty()) {
                continue;
            }

            context = context.editChild(child, editor -> editor.name(subCommand.name())
                .aliases(safeList(subCommand.aliases()))
                .applyMeta(editPermissions(safeList(subCommand.permissions())))
                .enabled(subCommand.isEnabled())
            );
        }

        return context
            .name(command.name())
            .aliases(safeList(command.aliases()))
            .applyMeta(editPermissions(safeList(command.permissions())))
            .enabled(command.isEnabled());
    }

    private boolean synchronizeDefaults(CommandBuilder<CommandSender> context) {
        this.commandConfiguration.commands = mutableMap(this.commandConfiguration.commands);
        boolean changed = this.commandConfiguration.commands.remove("") != null;

        Command current = this.commandConfiguration.commands.get(context.name());

        if (current == null) {
            current = new Command(
                context.name(),
                new ArrayList<>(context.aliases()),
                extractPermissions(context.meta()),
                context.isEnabled()
            );
            current.subCommands = new LinkedHashMap<>();

            this.commandConfiguration.commands.put(context.name(), current);
            changed = true;
        }

        current.subCommands = mutableMap(current.subCommands);

        for (CommandBuilder<CommandSender> child : context.children()) {
            if (current.subCommands.containsKey(child.name())) {
                continue;
            }

            current.subCommands.put(
                child.name(),
                new SubCommand(
                    child.name(),
                    child.isEnabled(),
                    new ArrayList<>(child.aliases()),
                    extractPermissions(child.meta())
                )
            );
            changed = true;
        }

        return changed;
    }

    private static List<String> extractPermissions(Meta meta) {
        Collection<PermissionSet> permissionSets = meta.get(Meta.PERMISSIONS);
        LinkedHashSet<String> permissions = new LinkedHashSet<>();

        for (PermissionSet permissionSet : permissionSets) {
            permissions.addAll(permissionSet.getPermissions());
        }

        return new ArrayList<>(permissions);
    }

    private static <K, V> Map<K, V> mutableMap(Map<K, V> map) {
        if (map == null) {
            return new LinkedHashMap<>();
        }

        return new LinkedHashMap<>(map);
    }

    private static List<String> safeList(List<String> value) {
        if (value == null) {
            return List.of();
        }

        return value;
    }

    private static UnaryOperator<Meta> editPermissions(List<String> permissions) {
        return meta -> meta.listEditor(Meta.PERMISSIONS)
            .clear()
            .add(new PermissionSet(permissions))
            .apply();
    }

}
