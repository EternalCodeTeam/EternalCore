package com.eternalcode.core.feature.chat;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.command.argument.DurationArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import dev.rollczi.litecommands.shared.EstimatedTemporalAmountParser;
import java.util.function.Supplier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

import java.time.Duration;

@Route(name = "chat")
@Permission("eternalcore.chat")
public class ChatManagerCommand {

    private final Supplier<Notice> clear;
    private final NoticeService noticeService;
    private final ChatManager chatManager;

    @Inject
    private ChatManagerCommand(ChatManager chatManager, NoticeService noticeService, ChatSettings settings) {
        this.noticeService = noticeService;
        this.chatManager = chatManager;
        this.clear = create(settings);
    }

    @Execute(route = "clear", aliases = "cc")
    @DescriptionDocs(description = "Clears chat")
    public void clear(CommandSender sender) {
        this.noticeService.create()
            .staticNotice(this.clear.get())
            .notice(translation -> translation.chat().cleared())
            .placeholder("{PLAYER}", sender.getName())
            .onlinePlayers()
            .send();
    }

    @Execute(route = "on")
    @DescriptionDocs(description = "Enables chat")
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
    @DescriptionDocs(description = "Disables chat")
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
    @DescriptionDocs(description = "Sets slowmode for chat", arguments = "<time>")
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

    public static Supplier<Notice> create(ChatSettings settings) {
        return () -> Notice.chat("<newline>".repeat(Math.max(0, settings.linesToClear())));
    }
}

