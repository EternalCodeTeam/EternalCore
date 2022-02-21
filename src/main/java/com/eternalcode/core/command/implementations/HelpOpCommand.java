/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

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

@Section(route = "helpop", aliases = { "report" })
@Permission("eternalcore.command.helpop")
@UsageMessage("&8» &cPoprawne użycie &7/helpop <text>")
public class HelpOpCommand {

    private final MessagesConfiguration messages;
    private final Server server;

    public HelpOpCommand(MessagesConfiguration messages, Server server) {
        this.messages = messages;
        this.server = server;
    }


    @Execute
    @MinArgs(1)
    public void execute(CommandSender sender, @Joiner String text) {
        this.server.getOnlinePlayers()
            .stream()
            .filter(players -> players.hasPermission("eternalcore.helpop.spy") || players.isOp())
            .forEach(players -> players.sendMessage(
                ChatUtils.color(StringUtils.replaceEach(this.messages.helpOpSection.format,
                    new String[]{ "{NICK}", "{TEXT}" },
                    new String[]{ sender.getName(), text }))));

        sender.sendMessage(ChatUtils.color(this.messages.helpOpSection.send));
    }
}
