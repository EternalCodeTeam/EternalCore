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

    private final AdventureNotification clear;
    private final NoticeService audiences;
    private final ChatManager chatManager;

    private ChatManagerCommand(ChatManager chatManager, NoticeService audiences, AdventureNotification clear) {
        this.audiences = audiences;
        this.chatManager = chatManager;
        this.clear = clear;
    }

    @Execute(route = "clear", aliases = "cc")
    public void clear(CommandSender sender) {
        this.audiences.create()
            .staticNotice(clear)
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

    public static ChatManagerCommand create(ChatManager chatManager, NoticeService audiences, int linesToClear) {
        Component clear = Component.empty();

        for (int lineIndex = 0; lineIndex < linesToClear; lineIndex++) {
            clear = clear.append(Component.newline());
        }

        return new ChatManagerCommand(chatManager, audiences, new AdventureNotification(clear, NoticeType.CHAT));
    }
}

