/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.MessageAction;
import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Joiner;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;

@Section(route = "alert", aliases = { "broadcast", "bc" })
@Permission("eternalcore.command.alert")
@UsageMessage("&8» &cPoprawne użycie &7/alert <title/actionbar/chat> <text>")
public class AlertCommand {

    private final MessagesConfiguration messages;
    private final Server server;

    public AlertCommand(MessagesConfiguration messages, Server server) {
        this.messages = messages;
        this.server = server;
    }

    @Execute
    @MinArgs(2)
    public void execute(@Arg(0) MessageAction messageAction, @Joiner String text) {
        Component component = ChatUtils.component(this.messages.otherMessages.alertMessagePrefix)
            .replaceText(builder -> builder.match("\\{BROADCAST}").replacement(text));

        this.server.getOnlinePlayers().forEach(player -> messageAction.action(player, component));
    }
}
