package com.eternalcode.core.feature.customcommand;

import com.eternalcode.multification.notice.Notice;
import java.io.Serializable;
import java.util.List;

public class CustomCommand implements Serializable {

    private String name;
    private List<String> aliases;
    private Notice message;

    public CustomCommand() {}

    public CustomCommand(String name, List<String> aliases, Notice message) {
        this.name = name;
        this.aliases = aliases;
        this.message = message;
    }

    public static CustomCommand of(String commandName, List<String> aliases, Notice message) {
        return new CustomCommand(commandName, aliases, message);
    }

    public String getName() {
        return name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    Notice getMessage() {
        return message;
    }
}
