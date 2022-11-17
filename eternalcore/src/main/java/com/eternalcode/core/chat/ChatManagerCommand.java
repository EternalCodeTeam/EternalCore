package com.eternalcode.core.chat;

import com.eternalcode.core.chat.adventure.AdventureNotification;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.chat.notification.NoticeType;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.time.Duration;

@Route(name = "chat", aliases = { "czat" })
@Permission("eternalcore.chat")
public class ChatManagerCommand {

    private static final AdventureNotification CLEAR;
    private static int CLEAR_LINE_COUNT;

    private final NoticeService audiences;
    private final ChatManager chatManager;

    static {
        Component clear = Component.empty();

        for (int lineIndex = 0; lineIndex < CLEAR_LINE_COUNT; lineIndex++) {
            clear = clear.append(Component.newline());
        }

        CLEAR = new AdventureNotification(clear, NoticeType.CHAT);
    }

    public ChatManagerCommand(ChatManager chatManager, NoticeService audiences, int linesToClear) {
        this.audiences = audiences;
        this.chatManager = chatManager;

        CLEAR_LINE_COUNT = linesToClear;
    }

    @Execute(route = "clear", aliases = "cc")
    public void clear(CommandSender sender) {
        this.audiences.create()
            .staticNotice(CLEAR)
            .message(messages -> messages.chat().cleared())
            .placeholder("{NICK}", sender.getName())
            .onlinePlayers()
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

    @Execute(route = "slowmode", required = 1)
    public void slowmode(Viewer viewer, @Arg @Name("time") Duration duration) {
        if (duration.isNegative()) {
            this.audiences.viewer(viewer, messages -> messages.argument().numberBiggerThanOrEqualZero());

            return;
        }

        this.chatManager.getChatSettings().setChatDelay(duration);

        String value = duration.toString().replace("PT", "");

        this.audiences.create()
            .message(messages -> messages.chat().slowModeSet())
            .placeholder("{SLOWMODE}", value.toLowerCase())
            .viewer(viewer)
            .send();
    }
}

