package com.eternalcode.core.feature.customcommand;

import com.eternalcode.multification.notice.Notice;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class CustomCommand implements Serializable {

    private String name;
    private List<String> aliases;
    private List<String> commands;
    private Notice message;

    public CustomCommand() {}

    public CustomCommand(String name, List<String> aliases, Notice message) {
        this.name = name;
        this.aliases = aliases;
        this.message = message;
        this.commands = Collections.emptyList();
    }

    public CustomCommand(String name, List<String> aliases, Notice message, List<String> commands) {
        this.name = name;
        this.aliases = aliases;
        this.message = message;
        this.commands = commands;
    }

    public static CustomCommand of(String commandName, List<String> aliases, Notice message) {
        return new CustomCommand(commandName, aliases, message);
    }

    public static CustomCommand of(String commandName, List<String> aliases, Notice message, List<String> commands) {
        return new CustomCommand(commandName, aliases, message, commands);
    }

    public String getName() {
        return name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public List<String> getCommands() {
        return commands;
    }

    Notice getMessage() {
        return message;
    }
}
