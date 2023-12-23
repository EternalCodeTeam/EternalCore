package com.eternalcode.core.bridge.litecommand.configurator.config;

import net.dzikoysk.cdn.entity.Contextual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Contextual
public class Command {

    public String name;
    public boolean enabled;
    public List<String> aliases = new ArrayList<>();
    public List<String> permissions = new ArrayList<>();
    public Map<String, SubCommand> subCommands = new HashMap<>();

    public Command() {}

    public Command(String name, List<String> aliases, List<String> permissions, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
        this.aliases = aliases;
        this.permissions = permissions;
    }

    public Command(String name, List<String> aliases, List<String> permissions, Map<String, SubCommand> subCommands, boolean enabled) {
        this.name = name;
        this.aliases = aliases;
        this.permissions = permissions;
        this.subCommands = subCommands;
        this.enabled = enabled;
    }

    public String name() {
        return this.name;
    }

    public List<String> aliases() {
        return this.aliases;
    }

    public List<String> permissions() {
        return this.permissions;
    }

    public Map<String, SubCommand> subCommands() {
        return this.subCommands;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
