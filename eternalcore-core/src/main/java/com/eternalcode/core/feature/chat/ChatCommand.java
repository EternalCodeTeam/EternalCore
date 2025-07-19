package com.eternalcode.core.feature.chat;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.scheduler.Scheduler;
import com.eternalcode.core.configuration.ConfigurationManager;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
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
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

@Command(name = "chat")
@Permission("eternalcore.chat")
class ChatCommand {

    private static final String EMPTY_CHAT_STRING = " ";

    private final NoticeService noticeService;
    private final ChatSettings chatSettings;
    private final EventCaller eventCaller;
    private final Server server;

    private final PluginConfiguration config;
    private final ConfigurationManager configManager;
    private final Scheduler scheduler;

    @Inject
    ChatCommand(
        NoticeService noticeService,
        ChatSettings chatSettings,
        EventCaller eventCaller, Server server,
        PluginConfiguration config,
        ConfigurationManager configManager, Scheduler scheduler
    ) {
        this.noticeService = noticeService;
        this.chatSettings = chatSettings;
        this.eventCaller = eventCaller;
        this.server = server;

        this.config = config;
        this.configManager = configManager;
        this.scheduler = scheduler;
    }

    @Execute(name = "clear", aliases = "cc")
    @DescriptionDocs(description = "Clears chat")
    void clear(@Context CommandSender sender) {
        ClearChatEvent event = this.eventCaller.callEvent(new ClearChatEvent(sender));

        if (event.isCancelled()) {
            return;
        }

        this.server.getOnlinePlayers().forEach(player -> {
            for (int i = 0; i < this.chatSettings.linesToClear(); i++) {
                player.sendMessage(EMPTY_CHAT_STRING);
            }
        });

        this.noticeService.create()
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

        Duration currentDelay = this.chatSettings.getChatDelay();
        EditSlowModeEvent event =
            this.eventCaller.callEvent(new EditSlowModeEvent(currentDelay, duration, viewer.getUniqueId()));

        if (event.isCancelled()) {
            return;
        }

        this.chatSettings.setChatDelay(duration);
        this.scheduler.runAsync(() -> this.configManager.save(this.config));

        if (duration.isZero()) {
            this.noticeService.create()
                .notice(translation -> translation.chat().slowModeOff())
                .placeholder("{PLAYER}", viewer.getName())
                .onlinePlayers()
                .send();

            return;
        }

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
}

