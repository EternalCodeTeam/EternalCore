package com.eternalcode.core.command.configurator;

import com.eternalcode.core.configuration.ReloadableConfig;
import com.eternalcode.core.feature.gamemode.GameModeArgumentSettings;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;
import org.bukkit.GameMode;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CommandConfiguration implements ReloadableConfig {

    public Argument argument = new Argument();

    @Contextual
    static class Argument implements GameModeArgumentSettings {

        @Description("# List of aliases for gamemode argument")
        Map<GameMode, List<String>> gameModeAliases = Map.of(
                GameMode.SURVIVAL, List.of("survival", "0"),
                GameMode.CREATIVE, List.of("creative", "1"),
                GameMode.ADVENTURE, List.of("adventure", "2"),
                GameMode.SPECTATOR, List.of("spectator", "3")
        );

        @Override
        public Optional<GameMode> getByAlias(String alias) {
            return this.gameModeAliases.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().contains(alias))
                    .map(Map.Entry::getKey)
                    .findFirst();
        }

        @Override
        public Collection<String> getAvailableAliases() {
            return this.gameModeAliases.values()
                    .stream()
                    .flatMap(Collection::stream)
                    .toList();
        }

    }

    @Description({
        "# This file allows you to configure commands.",
        "# You can change command name, aliases and permissions.",
        "# You can edit the commands as follows this template:",
        "# commands:",
        "#   <command_name>:",
        "#     name: \"<new_command_name>\"",
        "#     aliases:",
        "#       - \"<new_command_aliases>\"",
        "#     permissions:",
        "#       - \"<new_command_permission>\"",
    })

    public Map<String, Command> commands = Map.of(
        "eternalcore", new Command("eternal-core", List.of("eternal"), List.of("eternalcore.eternalcore"))
    );

    @Contextual
    public static class Command {
        public String name;
        public List<String> aliases;
        public List<String> permissions;

        public Command() {
        }

        public Command(String name, List<String> aliases, List<String> permissions) {
            this.name = name;
            this.aliases = aliases;
            this.permissions = permissions;
        }
    }

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "commands.yml");
    }

}
