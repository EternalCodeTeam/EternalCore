package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Joiner;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

@Section(route = "adminchat", aliases = "ac")
@Permission("eternalcore.command.adminchat")
@UsageMessage("&8» &cPoprawne użycie &7/adminchat <text>")
public class AdminChatCommand {

    private final MessagesConfiguration messages;
    private final Server server;

    public AdminChatCommand(MessagesConfiguration messages, Server server) {
        this.messages = messages;
        this.server = server;
    }

    @Execute
    @MinArgs(1)
    public void execute(CommandSender sender, @Joiner String text) {
        this.server.getOnlinePlayers()
            .stream()
            .filter(players -> players.hasPermission("eternalcore.adminchat.spy"))
            .forEach(players -> players.sendMessage(
                ChatUtils.color(StringUtils.replaceEach(
                    this.messages.adminChatSection.format,
                    new String[]{ "{NICK}", "{TEXT}" },
                    new String[]{ sender.getName(), text }))));
    }
}
