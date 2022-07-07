package com.eternalcode.core.chat;

import com.eternalcode.core.chat.adventure.AdventureNotification;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import panda.std.Option;

@Section(route = "chat", aliases = { "czat" })
@Permission("eternalcore.chat")
public class ChatManagerCommand {

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

    public ChatManagerCommand(ChatManager chatManager, NoticeService audiences) {
        this.audiences = audiences;
        this.chatManager = chatManager;
    }

    @Execute(route = "clear", aliases = "cc")
    public void clear(CommandSender sender) {
        this.audiences.create()
            .staticNotice(CLEAR)
            .message(messages -> messages.chat().cleared())
            .placeholder("{NICK}", sender.getName())
            .allPlayers()
            .send();
    }

    @Execute(route = "on")
    public void enable(Viewer viewer, CommandSender sender) {
        if (this.chatManager.getChatSettings().isChatEnabled()) {
            this.audiences.viewer(viewer, messages -> messages.chat().alreadyEnabled());
            return;
        }

        this.chatManager.getChatSettings().setChatEnabled(true);

        this.audiences.create()
            .message(messages -> messages.chat().enabled())
            .placeholder("{NICK}", sender.getName())
            .all()
            .send();
    }

    @Execute(route = "off")
    public void disable(Viewer viewer, CommandSender sender) {
        if (!this.chatManager.getChatSettings().isChatEnabled()) {
            this.audiences.viewer(viewer, messages -> messages.chat().alreadyDisabled());
            return;
        }

        this.chatManager.getChatSettings().setChatEnabled(false);

        this.audiences.create()
            .message(messages -> messages.chat().disabled())
            .placeholder("{NICK}", sender.getName())
            .all()
            .send();
    }

    @Execute(route = "slowmode")
    @Min(1)
    public void slowmode(Viewer viewer, String[] args) { // TODO: Argument (String[] args <- legacy solution)
        String amountArg = args[1];

        Option.attempt(NumberFormatException.class, () -> Double.parseDouble(amountArg)).peek(amount -> {
            if (amount < 0.0D) {
                this.audiences.viewer(viewer, messages -> messages.argument().numberBiggerThanOrEqualZero());
                return;
            }

            this.chatManager.getChatSettings().setChatDelay(amount);
            this.audiences.create()
                .message(messages -> messages.chat().slowModeSet())
                .placeholder("{SLOWMODE}", amountArg)
                .viewer(viewer)
                .send();

        }).onEmpty(() -> this.audiences.viewer(viewer, messages -> messages.argument().notNumber()));
    }
}

