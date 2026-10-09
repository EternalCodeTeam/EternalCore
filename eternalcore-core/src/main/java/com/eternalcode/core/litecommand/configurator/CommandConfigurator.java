package com.eternalcode.core.litecommand.configurator;


import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.litecommand.configurator.config.Command;
import com.eternalcode.core.litecommand.configurator.config.CommandConfiguration;
import com.eternalcode.core.litecommand.configurator.config.SubCommand;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.injector.annotations.lite.LiteCommandEditor;
import dev.rollczi.litecommands.command.builder.CommandBuilder;
import dev.rollczi.litecommands.editor.Editor;
import dev.rollczi.litecommands.meta.Meta;
import dev.rollczi.litecommands.permission.PermissionSet;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;
import org.bukkit.command.CommandSender;

@LiteCommandEditor
class CommandConfigurator implements Editor<CommandSender> {

    private final CommandConfiguration commandConfiguration;
    private final ConfigurationManager configurationManager;
    private final Scheduler scheduler;
    private final Logger logger;
    private final Set<String> discoveredRuntimeCommands = ConcurrentHashMap.newKeySet();
    private volatile boolean staleCleanupScheduled;

    @Inject
    CommandConfigurator(
        CommandConfiguration commandConfiguration,
        ConfigurationManager configurationManager,
        Scheduler scheduler,
        Logger logger
    ) {
        this.commandConfiguration = commandConfiguration;
        this.configurationManager = configurationManager;
        this.scheduler = scheduler;
        this.logger = logger;
    }

    @Override
    public CommandBuilder<CommandSender> edit(CommandBuilder<CommandSender> context) {
        if (context.isRoot()) {
            this.scheduleStaleCleanupOnce();
            return context;
        }

        if (context.name() == null || context.name().isBlank()) {
            return context;
        }

        String commandName = context.name();
        this.discoveredRuntimeCommands.add(commandName);

        boolean changed = this.synchronizeDefaults(context);
        if (changed) {
            this.configurationManager.save(this.commandConfiguration);
        }

        Command command = this.commandConfiguration.commands.get(commandName);

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

    private void scheduleStaleCleanupOnce() {
        if (this.staleCleanupScheduled) {
            return;
        }

        this.staleCleanupScheduled = true;
        this.scheduler.runLater(this::cleanupStaleCommands, Duration.ofSeconds(1));
    }

    private void cleanupStaleCommands() {
        this.commandConfiguration.commands = mutableMap(this.commandConfiguration.commands);
        boolean changed = this.commandConfiguration.commands.remove("") != null;

        if (changed) {
            this.logger.warning("[CommandConfigurator] Removed invalid empty command key ('') from commands.yml");
        }

        Set<String> runtimeNames = new HashSet<>(this.discoveredRuntimeCommands);
        int configuredCount = this.commandConfiguration.commands.size();
        int discoveredCount = runtimeNames.size();

        if (configuredCount > 0 && discoveredCount == 0) {
            this.logger.warning("[CommandConfigurator] Skipped stale cleanup because no runtime commands were discovered.");
            if (changed) {
                this.configurationManager.save(this.commandConfiguration);
            }
            return;
        }

        List<String> staleKeys = this.commandConfiguration.commands.entrySet().stream()
            .filter(entry -> entry.getValue() == null || !runtimeNames.contains(entry.getKey()))
            .map(Map.Entry::getKey)
            .toList();

        for (String staleKey : staleKeys) {
            this.commandConfiguration.commands.remove(staleKey);
            this.logger.warning("[CommandConfigurator] Removed stale command from commands.yml: '" + staleKey + "'");
            changed = true;
        }

        if (changed) {
            this.configurationManager.save(this.commandConfiguration);
        }
    }

    private boolean synchronizeDefaults(CommandBuilder<CommandSender> context) {
        this.commandConfiguration.commands = mutableMap(this.commandConfiguration.commands);
        boolean changed = false;

        if (this.commandConfiguration.commands.remove("") != null) {
            this.logger.warning("[CommandConfigurator] Removed invalid empty command key ('') from commands.yml");
            changed = true;
        }

        String runtimeName = context.name();
        Command current = this.commandConfiguration.commands.get(runtimeName);

        if (current == null) {
            current = new Command(
                runtimeName,
                new ArrayList<>(context.aliases()),
                extractPermissions(context.meta()),
                context.isEnabled()
            );
            current.subCommands = new LinkedHashMap<>();

            this.commandConfiguration.commands.put(runtimeName, current);
            this.logger.info("[CommandConfigurator] Added new command defaults to commands.yml: '" + runtimeName + "'");
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
            this.logger.info(
                "[CommandConfigurator] Added missing subcommand defaults for '" + runtimeName + "': '" + child.name() + "'"
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
