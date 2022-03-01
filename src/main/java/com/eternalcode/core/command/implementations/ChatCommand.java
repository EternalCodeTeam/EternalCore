/*
 * Copyright (c) 2022. EternalCode.pl
 */

package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.ChatManager;
import com.eternalcode.core.chat.notification.AudiencesService;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import panda.std.Option;

@Section(route = "chat", aliases = { "czat" })
@Permission("eternalcore.command.chat")
@UsageMessage("&cPoprawne użycie &7/chat <clear/on/off/slowmode [time]>")
public class ChatCommand {

    private static final Component CLEAR;

    static {
        Component clear = Component.empty();

        for (int line = 0; line < 64; line++) {
            clear = clear.append(Component.newline());
        }

        CLEAR = clear;
    }

    private final AudiencesService audiences;
    private final ChatManager chatManager;

    public ChatCommand(ChatManager chatManager, AudiencesService audiences) {
        this.audiences = audiences;
        this.chatManager = chatManager;
    }

    @Execute(route = "clear")
    public void clear(CommandSender sender) {
        this.audiences.notice()
            .allPlayers()
            .staticNotice(CLEAR)
            .message(messages -> messages.chat().cleared())
            .placeholder("{NICK}", sender.getName());
    }

    @Execute(route = "on")
    public void enable(CommandSender sender) {
        if (this.chatManager.getChatSettings().isChatEnabled()) {
            this.audiences.sender(sender, messages -> messages.chat().alreadyEnabled());
            return;
        }

        this.chatManager.getChatSettings().setChatEnabled(true);
        this.audiences.notice()
            .all()
            .message(messages -> messages.chat().enabled())
            .placeholder("{NICK}", sender.getName())
            .send();
    }

    @Execute(route = "off")
    public void disable(CommandSender sender) {
        if (!this.chatManager.getChatSettings().isChatEnabled()) {
            this.audiences.sender(sender, messages -> messages.chat().alreadyDisabled());
            return;
        }

        this.chatManager.getChatSettings().setChatEnabled(false);
        this.audiences.notice()
            .all()
            .message(messages -> messages.chat().disabled())
            .placeholder("{NICK}", sender.getName())
            .send();
    }

    @Execute(route = "slowmode")
    @MinArgs(1)
    public void slowmode(CommandSender sender, String[] args) {
        String amountArg = args[1];

        Option.attempt(NumberFormatException.class, () -> Double.parseDouble(amountArg)).peek(amount -> {
            if (amount < 0.0D) {
                this.audiences.sender(sender, messages -> messages.argument().numberBiggerThanOrEqualZero());
                return;
            }

            this.chatManager.getChatSettings().setChatDelay(amount);
            this.audiences.notice()
                .sender(sender)
                .message(messages -> messages.chat().slowModeSet())
                .placeholder("{SLOWMODE}", amountArg).send();

        }).onEmpty(() -> this.audiences.sender(sender, messages -> messages.argument().notNumber()));
    }
}

