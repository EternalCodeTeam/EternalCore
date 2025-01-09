package com.eternalcode.core.feature.chat;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.chat.event.ClearChatEvent;
import com.eternalcode.core.feature.chat.event.DisableChatEvent;
import com.eternalcode.core.feature.chat.event.EditSlowModeEvent;
import com.eternalcode.core.feature.chat.event.EnableChatEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.multification.notice.Notice;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import java.util.function.Supplier;
import org.bukkit.command.CommandSender;

@Command(name = "chat")
@Permission("eternalcore.chat")
class ChatCommand {

    private final NoticeService noticeService;
    private final ChatSettings chatSettings;
    private final EventCaller eventCaller;

    private final Supplier<Notice> clear;

    @Inject
    ChatCommand(
        NoticeService noticeService,
        ChatSettings chatSettings,
        EventCaller eventCaller
    ) {
        this.noticeService = noticeService;
        this.chatSettings = chatSettings;

        this.clear = create(chatSettings);
        this.eventCaller = eventCaller;
    }

    @Execute(name = "clear", aliases = "cc")
    @DescriptionDocs(description = "Clears chat")
    void clear(@Context CommandSender sender) {
        ClearChatEvent event = this.eventCaller.callEvent(new ClearChatEvent(sender));

        if (event.isCancelled()) {
            return;
        }

        this.noticeService.create()
            .notice(this.clear.get())
            .notice(translation -> translation.chat().cleared())
            .placeholder("{PLAYER}", sender.getName())
            .onlinePlayers()
            .send();
    }

    @Execute(name = "on")
    @DescriptionDocs(description = "Enables chat")
    void enable(@Context Viewer viewer, @Context CommandSender sender) {
        if (this.chatSettings.isChatEnabled()) {
            this.noticeService.viewer(viewer, translation -> translation.chat().alreadyEnabled());
            return;
        }

        EnableChatEvent event = this.eventCaller.callEvent(new EnableChatEvent(sender));

        if (event.isCancelled()) {
            return;
        }

        this.chatSettings.setChatEnabled(true);

        this.noticeService.create()
            .notice(translation -> translation.chat().enabled())
            .placeholder("{PLAYER}", sender.getName())
            .onlinePlayers()
            .send();
    }

    @Execute(name = "off")
    @DescriptionDocs(description = "Disables chat")
    void disable(@Context Viewer viewer, @Context CommandSender sender) {
        if (!this.chatSettings.isChatEnabled()) {
            this.noticeService.viewer(viewer, translation -> translation.chat().alreadyDisabled());
            return;
        }

        DisableChatEvent event = this.eventCaller.callEvent(new DisableChatEvent(sender));

        if (event.isCancelled()) {
            return;
        }

        this.chatSettings.setChatEnabled(false);

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

            this.chatSettings.setChatDelay(duration);
            return;
        }

        Duration chatDelay = this.chatSettings.getChatDelay();
        EditSlowModeEvent event = this.eventCaller.callEvent(new EditSlowModeEvent(chatDelay, duration, viewer.getUniqueId()));

        if (event.isCancelled()) {
            return;
        }

        this.chatSettings.setChatDelay(duration);

        this.noticeService.create()
            .notice(translation -> translation.chat().slowModeSet())
            .placeholder("{SLOWMODE}", DurationUtil.format(duration, true))
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

