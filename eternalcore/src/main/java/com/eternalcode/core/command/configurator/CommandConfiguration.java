package com.eternalcode.core.command.configurator;

import com.eternalcode.core.configuration.ReloadableConfig;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.util.List;
import java.util.Map;

public class CommandConfiguration implements ReloadableConfig {

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "commands.yml");
    }

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

}
