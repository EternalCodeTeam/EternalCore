/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "helpop", aliases = {"report"})
@Permission("eternalcore.command.helpop")
@UsageMessage("&8» &cPoprawne użycie &7/helpop <text>")
public class HelpOpCommand {

    private final MessagesConfiguration message;

    public HelpOpCommand(MessagesConfiguration message) {
        this.message = message;
    }

    @Execute
    @MinArgs(1)
    public void execute(CommandSender sender, String[] args) {
        String text = StringUtils.join(args, " ", 0, args.length);

        String helpOpFormat = ChatUtils.color(message.messagesSection.helpOpFormat.replace("{TEXT}", text));


        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("eternalcore.helpop.spy") || players.isOp()) {
                players.sendMessage(helpOpFormat);
            }
        }

        sender.sendMessage(ChatUtils.color(message.messagesSection.helpOpSend));
    }
}
