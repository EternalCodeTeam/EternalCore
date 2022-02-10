/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.configuration.MessagesConfiguration;
import com.eternalcode.core.managers.chat.ChatManager;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import dev.rollczi.litecommands.valid.Valid;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import panda.std.Option;

import java.util.function.Function;
import java.util.stream.IntStream;

@Section (route = "chat", aliases = { "czat" })
@Permission ("eternalcore.command.chat")
@UsageMessage ("&cPoprawne użycie &7/chat <clear/on/off/slowmode [time]>")
public class ChatCommand {

    private final MessagesConfiguration message;
    private final ChatManager chatManager;

    public ChatCommand (MessagesConfiguration message, EternalCore core) {
        this.message = message;
        this.chatManager = core.getChatManager();
    }

    @Execute (route = "clear")
    public void clear (CommandSender sender) {
        IntStream.range(0, 256).mapToObj(i -> Bukkit.getOnlinePlayers().stream()).flatMap(Function.identity()).forEach(player -> player.sendMessage(" "));
        Bukkit.broadcast(ChatUtils.component(message.messagesSection.chatCleared.replace("{NICK}", sender.getName())));
    }

    @Execute (route = "on")
    public void enable (CommandSender sender) {
        if (this.chatManager.isChatEnabled()) {
            sender.sendMessage(ChatUtils.component(message.messagesSection.chatAlreadyEnabled));
            return;
        }
        this.chatManager.setChatEnabled(true);
        Bukkit.broadcast(ChatUtils.component(message.messagesSection.chatEnabled.replace("{NICK}", sender.getName())));
    }

    @Execute (route = "off")
    public void disable (CommandSender sender) {
        if (!this.chatManager.isChatEnabled()) {
            sender.sendMessage(ChatUtils.component(message.messagesSection.chatAlreadyDisabled));
            return;
        }
        this.chatManager.setChatEnabled(false);
        Bukkit.broadcast(ChatUtils.component(message.messagesSection.chatDisabled.replace("{NICK}", sender.getName())));
    }

    @Execute (route = "slowmode")
    @MinArgs (1)
    public void slowmode (CommandSender sender, String[] args) {
        Option.attempt(NumberFormatException.class, () -> Double.parseDouble(args[1])).peek(amount -> {
            Valid.when(amount < 0.0D, message.messagesSection.numberBiggerThanOrEqualZero);

            chatManager.setChatDelay(amount);
            sender.sendMessage(ChatUtils.color(message.messagesSection.chatSlowModeSet.replace("{SLOWMODE}", args[1])));
        }).orThrow(() -> new ValidationCommandException(message.messagesSection.notNumber));
    }
}

