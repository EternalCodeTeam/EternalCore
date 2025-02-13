package com.eternalcode.core.feature.adminchat;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.adminchat.event.AdminChatEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.multification.notice.NoticeBroadcast;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

@Command(name = "adminchat", aliases = "ac")
@Permission("eternalcore.adminchat")
class AdminChatCommand {

    private static final String ADMIN_CHAT_SPY_PERMISSION = "eternalcore.adminchat.spy";

    private final NoticeService noticeService;
    private final EventCaller eventCaller;
    private final Server server;

    @Inject
    AdminChatCommand(NoticeService noticeService, EventCaller eventCaller, Server server) {
        this.noticeService = noticeService;
        this.eventCaller = eventCaller;
        this.server = server;
    }

    @Execute
    @DescriptionDocs(description = "Sends a message to all staff members with eternalcore.adminchat.spy permissions", arguments = "<message>")
    void execute(@Context CommandSender sender, @Join String message) {
        AdminChatEvent event = this.eventCaller.callEvent(new AdminChatEvent(sender, message));

        if (event.isCancelled()) {
            return;
        }

        String eventMessage = event.getContent();

        NoticeBroadcast notice = this.noticeService.create()
            .console()
            .notice(translation -> translation.adminChat().format())
            .placeholder("{PLAYER}", sender.getName())
            .placeholder("{TEXT}", eventMessage);

        this.server.getOnlinePlayers().stream()
            .filter(player -> player.hasPermission(ADMIN_CHAT_SPY_PERMISSION))
            .forEach(player -> notice.player(player.getUniqueId()));

        notice.send();
    }
}
