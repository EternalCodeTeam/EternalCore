package com.eternalcode.core.command.configurator.config;

import net.dzikoysk.cdn.entity.Contextual;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Contextual
public class Command {

    private String name;
    private boolean disabled;
    private List<String> aliases;
    private List<String> permissions;
    private Map<String, SubCommand> subCommands = new HashMap<>();

    public Command() {
    }

    public Command(String name, List<String> aliases, List<String> permissions, boolean disabled) {
        this.name = name;
        this.disabled = disabled;
        this.aliases = aliases;
        this.permissions = permissions;
    }

    public Command(String name, List<String> aliases, List<String> permissions, Map<String, SubCommand> subCommands, boolean disabled) {
        this.name = name;
        this.aliases = aliases;
        this.permissions = permissions;
        this.subCommands = subCommands;
        this.disabled = disabled;
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

    public boolean isDisabled() {
        return this.disabled;
    }
}
