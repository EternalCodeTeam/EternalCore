package com.eternalcode.core.bridge.litecommand.configurator.config;

import com.eternalcode.core.configuration.AbstractConfigurationFile;
import com.eternalcode.core.feature.gamemode.GameModeArgumentSettings;
import com.eternalcode.core.injector.annotations.Bean;
import com.eternalcode.core.injector.annotations.component.ConfigurationFile;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.GameMode;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ConfigurationFile
public class CommandConfiguration extends AbstractConfigurationFile {

    @Bean
    public Argument argument = new Argument();

    @Override
    public File getConfigFile(File dataFolder) {
        return new File(dataFolder, "commands.yml");
    }

    public static class Argument extends OkaeriConfig implements GameModeArgumentSettings {

        @Comment("# List of aliases for gamemode argument")
        public Map<GameMode, List<String>> gameModeAliases = Map.of(
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

    @Comment("# List of shortcuts for gamemode command")
    public Map<GameMode, List<String>> gameModeShortCuts = Map.of(
                GameMode.SURVIVAL, Collections.singletonList("gms"),
                GameMode.CREATIVE, Collections.singletonList("gmc"),
                GameMode.ADVENTURE, Collections.singletonList("gma"),
                GameMode.SPECTATOR, Collections.singletonList("gmsp")
    );

    public List<String> getGameModeShortCuts() {
        return this.gameModeShortCuts.values()
                .stream()
                .flatMap(Collection::stream)
                .toList();
    }

    public GameMode getGameMode(String label) {
        return this.gameModeShortCuts.entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(label))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    @Comment({
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
        "#     subCommands:",
        "#       <default_sub_command_name>:",
        "#         name: <new_sub_command_name>",
        "#         disabled: false/true",
        "#         aliases:",
        "#           - \"<new_sub_command_aliases>\"",
        "#         permissions:",
        "#           - \"<new_sub_command_permission>\"",
    })

    public Map<String, Command> commands = Map.of(
        "eternalcore", new Command(
            "eternal-core",
            List.of("eternal"),
            List.of("eternalcore.eternalcore"),
            Map.of("reload", new SubCommand("reload", true, List.of("rl"), List.of("eternalcore.reload"))),
            true)
    );

}
