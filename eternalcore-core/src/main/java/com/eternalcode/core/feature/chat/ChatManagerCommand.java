package com.eternalcode.core.feature.chat;

import com.eternalcode.annotations.scan.command.DocsDescription;
import com.eternalcode.core.command.argument.DurationArgument;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.notification.NoticeType;
import com.eternalcode.core.notification.adventure.AdventureNotification;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.shared.EstimatedTemporalAmountParser;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import java.time.Duration;

@Route(name = "chat")
@Permission("eternalcore.chat")
public class ChatManagerCommand {

    private final AdventureNotification clear;
    private final NoticeService noticeService;
    private final ChatManager chatManager;

    private ChatManagerCommand(ChatManager chatManager, NoticeService noticeService, AdventureNotification clear) {
        this.noticeService = noticeService;
        this.chatManager = chatManager;
        this.clear = clear;
    }

    @Execute(route = "clear", aliases = "cc")
    @DocsDescription(description = "Clears chat")
    public void clear(CommandSender sender) {
        this.noticeService.create()
            .staticNotice(this.clear)
            .notice(translation -> translation.chat().cleared())
            .placeholder("{PLAYER}", sender.getName())
            .onlinePlayers()
            .send();
    }

    @Execute(route = "on")
    @DocsDescription(description = "Enables chat")
    public void enable(Viewer viewer, CommandSender sender) {
        if (this.chatManager.getChatSettings().isChatEnabled()) {
            this.noticeService.viewer(viewer, translation -> translation.chat().alreadyEnabled());
            return;
        }

        this.chatManager.getChatSettings().setChatEnabled(true);

        this.noticeService.create()
            .notice(translation -> translation.chat().enabled())
            .placeholder("{PLAYER}", sender.getName())
            .onlinePlayers()
            .send();
    }

    @Execute(route = "off")
    @DocsDescription(description = "Disables chat")
    public void disable(Viewer viewer, CommandSender sender) {
        if (!this.chatManager.getChatSettings().isChatEnabled()) {
            this.noticeService.viewer(viewer, translation -> translation.chat().alreadyDisabled());
            return;
        }

        this.chatManager.getChatSettings().setChatEnabled(false);

        this.noticeService.create()
            .notice(translation -> translation.chat().disabled())
            .placeholder("{PLAYER}", sender.getName())
            .onlinePlayers()
            .send();
    }

    @Execute(route = "slowmode", required = 1)
    @DocsDescription(description = "Sets slowmode for chat", arguments = "<time>")
    public void slowmode(Viewer viewer, @Arg @By(DurationArgument.KEY) Duration duration) {
        if (duration.isNegative()) {
            this.noticeService.viewer(viewer, translation -> translation.argument().numberBiggerThanOrEqualZero());

            return;
        }

        this.chatManager.getChatSettings().setChatDelay(duration);

        this.noticeService.create()
            .notice(translation -> translation.chat().slowModeSet())
            .placeholder("{SLOWMODE}", EstimatedTemporalAmountParser.TIME_UNITS.format(duration))
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

