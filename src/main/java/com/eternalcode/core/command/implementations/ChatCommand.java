/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.ChatManager;
import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.resources.ValidationException;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.command.CommandSender;

import static com.eternalcode.core.command.Valid.when;

@FunnyComponent
public final class ChatCommand {

    private final ChatManager chatManager;

    public ChatCommand(ChatManager chatManager) {
        this.chatManager = chatManager;
    }

    @FunnyCommand(
        name = "chat",
        aliases = {"czat"},
        permission = "eternalcore.command.chat",
        usage = "&cPoprawne użycie &7/chat <clear/on/off/slowmode> [time]",
        acceptsExceeded = true
    )

    public void execute(CommandSender sender, String[] args, CommandInfo commandInfo) {
        when(args.length < 1, commandInfo.getUsageMessage());

        switch (args[0].toLowerCase()) {
            case "clear" -> this.chatManager.clearChat(sender);
            case "on" -> this.chatManager.switchChat(sender, true);
            case "off" -> this.chatManager.switchChat(sender, false);
            case "slowmode" -> {
                when(args.length != 2, "&cPoprawne użycie &7/chat slowmode [time]");
                this.chatManager.slowMode(sender, args[1]);
            }
            default -> throw new ValidationException(commandInfo.getUsageMessage());
        }
    }
}

