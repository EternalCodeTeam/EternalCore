package com.eternalcode.core.command.configurator.config;

import java.util.List;

public class SubCommand {

    private String name;
    private boolean disabled;
    private List<String> aliases;
    private List<String> permissions;

    public SubCommand() {
    }

    public SubCommand(String name, boolean disabled, List<String> aliases, List<String> permissions) {
        this.name = name;
        this.disabled = disabled;
        this.aliases = aliases;
        this.permissions = permissions;
    }

    public String name() {
        return this.name;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public List<String> aliases() {
        return this.aliases;
    }

    public List<String> permissions() {
        return this.permissions;
    }
}
