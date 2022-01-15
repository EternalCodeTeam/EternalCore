/*
 * Copyright (c) 2021. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.chat.ChatManager;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import net.dzikoysk.funnycommands.commands.CommandInfo;
import net.dzikoysk.funnycommands.resources.ValidationException;
import net.dzikoysk.funnycommands.stereotypes.FunnyCommand;
import net.dzikoysk.funnycommands.stereotypes.FunnyComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import panda.std.Option;

import static com.eternalcode.core.command.Valid.when;

@FunnyComponent
public final class ChatCommand {

    private final ConfigurationManager  configurationManager;
    private final ChatManager chatManager;

    public ChatCommand(EternalCore core) {
        this.chatManager = core.getChatManager();
        this.configurationManager = core.getConfigurationManager();
    }

    @FunnyCommand(
        name = "chat",
        aliases = {"czat"},
        permission = "eternalcore.command.chat",
        usage = "&cPoprawne użycie &7/chat <clear/on/off/slowmode [time]>",
        acceptsExceeded = true
    )

    public void execute(CommandSender sender, String[] args, CommandInfo commandInfo) {
        when(args.length < 1, commandInfo.getUsageMessage());

        MessagesConfiguration messages = this.configurationManager.getMessagesConfiguration();

        switch (args[0].toLowerCase()) {
            case "clear" -> this.chatManager.clearChat(sender);
            case "on" -> {
                if (this.chatManager.isEnabled()){
                    sender.sendMessage(ChatUtils.component(messages.chatAlreadyEnabled));
                    return;
                }
                this.chatManager.setEnabled(true);
                Bukkit.broadcast(ChatUtils.component(messages.chatEnabled.replace("{NICK}", sender.getName())));
            }

            case "off" -> {
                if (!this.chatManager.isEnabled()){
                    sender.sendMessage(ChatUtils.component(messages.chatAlreadyDisabled));
                    return;
                }
                this.chatManager.setEnabled(false);
                Bukkit.broadcast(ChatUtils.component(messages.chatDisabled.replace("{NICK}", sender.getName())));
            }

            case "slowmode" -> {
                when(args.length != 2, "&cPoprawne użycie &7/chat slowmode [time]");
                Option.attempt(NumberFormatException.class, () -> Integer.parseInt(args[1])).peek(amount -> {
                    when(amount >= 0, messages.numberBiggerThanZero);

                    this.chatManager.slowMode(sender, args[1]);
                }).orThrow(() -> new ValidationException(ChatUtils.color(messages.notNumber)));
            }
            default -> throw new ValidationException(commandInfo.getUsageMessage());
        }
    }
}

