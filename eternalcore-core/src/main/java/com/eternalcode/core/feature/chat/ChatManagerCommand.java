package com.eternalcode.core.feature.chat;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.Notice;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import java.util.function.Supplier;

import dev.rollczi.litecommands.time.DurationParser;
import org.bukkit.command.CommandSender;

import java.time.Duration;

@Command(name = "chat")
@Permission("eternalcore.chat")
class ChatManagerCommand {

    private final Supplier<Notice> clear;
    private final NoticeService noticeService;
    private final ChatManagerServiceImpl chatManagerServiceImpl;

    @Inject
    ChatManagerCommand(ChatManagerServiceImpl chatManagerServiceImpl, NoticeService noticeService, ChatSettings settings) {
        this.noticeService = noticeService;
        this.chatManagerServiceImpl = chatManagerServiceImpl;
        this.clear = create(settings);
    }

    @Execute(name = "clear", aliases = "cc")
    @DescriptionDocs(description = "Clears chat")
    void clear(@Context CommandSender sender) {
        this.noticeService.create()
            .staticNotice(this.clear.get())
            .notice(translation -> translation.chat().cleared())
            .placeholder("{PLAYER}", sender.getName())
            .onlinePlayers()
            .send();
    }

    @Execute(name = "on")
    @DescriptionDocs(description = "Enables chat")
    void enable(@Context Viewer viewer, @Context CommandSender sender) {
        if (this.chatManagerServiceImpl.getChatSettings().isChatEnabled()) {
            this.noticeService.viewer(viewer, translation -> translation.chat().alreadyEnabled());
            return;
        }

        this.chatManagerServiceImpl.getChatSettings().setChatEnabled(true);

        this.noticeService.create()
            .notice(translation -> translation.chat().enabled())
            .placeholder("{PLAYER}", sender.getName())
            .onlinePlayers()
            .send();
    }

    @Execute(name = "off")
    @DescriptionDocs(description = "Disables chat")
    void disable(@Context Viewer viewer, @Context CommandSender sender) {
        if (!this.chatManagerServiceImpl.getChatSettings().isChatEnabled()) {
            this.noticeService.viewer(viewer, translation -> translation.chat().alreadyDisabled());
            return;
        }

        this.chatManagerServiceImpl.getChatSettings().setChatEnabled(false);

        this.noticeService.create()
            .notice(translation -> translation.chat().disabled())
            .placeholder("{PLAYER}", sender.getName())
            .onlinePlayers()
            .send();
    }

    @Execute(name = "slowmode")
    @DescriptionDocs(description = "Sets slowmode for chat", arguments = "<time>")
    void slowmode(@Context Viewer viewer, @Arg Duration duration) {
        if (duration.isNegative()) {
            this.noticeService.viewer(viewer, translation -> translation.argument().numberBiggerThanOrEqualZero());

            return;
        }

        if (duration.isZero()) {
            this.noticeService.create()
                    .notice(translation -> translation.chat().slowModeOff())
                    .placeholder("{PLAYER}", viewer.getName())
                    .onlinePlayers()
                    .send();

            this.chatManagerServiceImpl.getChatSettings().setChatDelay(duration);
            return;
        }

        this.chatManagerServiceImpl.getChatSettings().setChatDelay(duration);

        this.noticeService.create()
            .notice(translation -> translation.chat().slowModeSet())
            .placeholder("{SLOWMODE}", DurationParser.TIME_UNITS.format(duration))
            .onlinePlayers()
            .send();
    }

    @Execute(name = "slowmode 0")
    @DescriptionDocs(description = "Disable SlowMode for chat")
    void slowmodeOff(@Context Viewer viewer) {
        Duration noSlowMode = Duration.ZERO;
        this.slowmode(viewer, noSlowMode);
    }

    private static Supplier<Notice> create(ChatSettings settings) {
        return () -> Notice.chat("<newline>".repeat(Math.max(0, settings.linesToClear())));
    }
}

