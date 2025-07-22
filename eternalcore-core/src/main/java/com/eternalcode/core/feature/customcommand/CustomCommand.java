package com.eternalcode.core.feature.customcommand;

import com.eternalcode.multification.notice.Notice;
import java.util.List;
import net.dzikoysk.cdn.entity.Contextual;

@Contextual
public class CustomCommand {

    public String commandName;
    public List<String> aliases;
    public Notice message;

    public CustomCommand() {}

    public CustomCommand(String commandName, List<String> aliases, Notice message) {
        this.commandName = commandName;
        this.aliases = aliases;
        this.message = message;
    }

    public String getCommandName() {
        return commandName;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public Notice getMessage() {
        return message;
    }
}
