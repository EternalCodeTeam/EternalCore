package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.ChatManager;
import com.eternalcode.core.chat.adventure.AdventureNotification;
import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
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

    private static final AdventureNotification CLEAR;

    static {
        Component clear = Component.empty();

        for (int line = 0; line < 64; line++) {
            clear = clear.append(Component.newline());
        }

        CLEAR = new AdventureNotification(clear, NoticeType.CHAT);
    }

    private final NoticeService audiences;
    private final ChatManager chatManager;

    public ChatCommand(ChatManager chatManager, NoticeService audiences) {
        this.audiences = audiences;
        this.chatManager = chatManager;
    }

    @Execute(route = "clear", aliases = "cc")
    public void clear(CommandSender sender) {
        this.audiences.notice()
            .staticNotice(CLEAR)
            .message(messages -> messages.chat().cleared())
            .placeholder("{NICK}", sender.getName())
            .allPlayers()
            .send();
    }

    @Execute(route = "on")
    public void enable(Audience audience, CommandSender sender) {
        if (this.chatManager.getChatSettings().isChatEnabled()) {
            this.audiences.audience(audience, messages -> messages.chat().alreadyEnabled());
            return;
        }

        this.chatManager.getChatSettings().setChatEnabled(true);



        this.audiences.notice()
            .message(messages -> messages.chat().enabled())
            .placeholder("{NICK}", sender.getName())
            .all()
            .send();
    }

    @Execute(route = "off")
    public void disable(Audience audience, CommandSender sender) {
        if (!this.chatManager.getChatSettings().isChatEnabled()) {
            this.audiences.audience(audience, messages -> messages.chat().alreadyDisabled());
            return;
        }

        this.chatManager.getChatSettings().setChatEnabled(false);

        this.audiences.notice()
            .message(messages -> messages.chat().disabled())
            .placeholder("{NICK}", sender.getName())
            .all()
            .send();
    }

    @Execute(route = "slowmode")
    @MinArgs(1)
    public void slowmode(Audience audience, String[] args) {
        String amountArg = args[1];

        Option.attempt(NumberFormatException.class, () -> Double.parseDouble(amountArg)).peek(amount -> {
            if (amount < 0.0D) {
                this.audiences.audience(audience, messages -> messages.argument().numberBiggerThanOrEqualZero());
                return;
            }

            this.chatManager.getChatSettings().setChatDelay(amount);
            this.audiences.notice()
                .message(messages -> messages.chat().slowModeSet())
                .placeholder("{SLOWMODE}", amountArg)
                .audience(audience)
                .send();

        }).onEmpty(() -> this.audiences.audience(audience, messages -> messages.argument().notNumber()));
    }
}

