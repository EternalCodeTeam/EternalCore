package com.eternalcode.core.feature.adminchat;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.adminchat.event.AdminChatEvent;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeBroadcast;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "adminchat", aliases = "ac")
@Permission("eternalcore.adminchat")
class AdminChatCommand {

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
        AdminChatEvent event = new AdminChatEvent(sender, message);

        this.eventCaller.callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        NoticeBroadcast notice = this.noticeService.create()
            .console()
            .notice(translation -> translation.adminChat().format())
            .placeholder("{PLAYER}", sender.getName())
            .placeholder("{TEXT}", message);

        for (Player player : this.server.getOnlinePlayers()) {
            if (!player.hasPermission("eternalcore.adminchat.spy")) {
                continue;
            }

            notice = notice.player(player.getUniqueId());
        }

        notice.send();
    }
    
}
