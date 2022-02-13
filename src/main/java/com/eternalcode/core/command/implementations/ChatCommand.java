/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.EternalCore;
import com.eternalcode.core.chat.ChatManager;
import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import dev.rollczi.litecommands.valid.Valid;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import panda.std.Option;

import java.util.function.Function;
import java.util.stream.IntStream;

@Section(route = "chat", aliases = { "czat" })
@Permission("eternalcore.command.chat")
@UsageMessage("&cPoprawne użycie &7/chat <clear/on/off/slowmode [time]>")
public class ChatCommand {

    private final MessagesConfiguration message;
    private final ChatManager chatManager;
    private final Server server;

    public ChatCommand(EternalCore core) {
        this.message = core.getConfigurationManager().getMessagesConfiguration();
        this.chatManager = core.getChatManager();
        this.server = core.getServer();
    }

    @Execute(route = "clear")
    public void clear(CommandSender sender) {
        IntStream.range(0, 256).mapToObj(i -> this.server.getOnlinePlayers().stream()).flatMap(Function.identity()).forEach(player -> player.sendMessage(" "));
        this.server.broadcast(ChatUtils.component(this.message.chatSection.cleared.replace("{NICK}", sender.getName())));
    }

    @Execute(route = "on")
    public void enable(CommandSender sender) {
        if (this.chatManager.isChatEnabled()) {
            sender.sendMessage(ChatUtils.component(this.message.chatSection.alreadyEnabled));
            return;
        }
        this.chatManager.setChatEnabled(true);
        this.server.broadcast(ChatUtils.component(this.message.chatSection.enabled.replace("{NICK}", sender.getName())));
    }

    @Execute(route = "off")
    public void disable(CommandSender sender) {
        if (!this.chatManager.isChatEnabled()) {
            sender.sendMessage(ChatUtils.component(this.message.chatSection.alreadyDisabled));
            return;
        }
        this.chatManager.setChatEnabled(false);
        this.server.broadcast(ChatUtils.component(this.message.chatSection.disabled.replace("{NICK}", sender.getName())));
    }

    @Execute(route = "slowmode")
    @MinArgs(1)
    public void slowmode(CommandSender sender, String[] args) {
        Option.attempt(NumberFormatException.class, () -> Double.parseDouble(args[1])).peek(amount -> {
            Valid.when(amount < 0.0D, this.message.argumentSection.numberBiggerThanOrEqualZero);

            this.chatManager.setChatDelay(amount);
            sender.sendMessage(ChatUtils.color(this.message.chatSection.slowModeSet.replace("{SLOWMODE}", args[1])));
        }).orThrow(() -> new ValidationCommandException(this.message.argumentSection.notNumber));
    }
}

