package com.eternalcode.core.feature.adminchat;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.event.EventCaller;
import com.eternalcode.core.feature.adminchat.event.AdminChatEvent;
import com.eternalcode.core.feature.adminchat.event.AdminChatService;
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
import org.bukkit.entity.Player;

@Command(name = "adminchat", aliases = "ac")
@Permission("eternalcore.adminchat")
class AdminChatCommand {
    private final AdminChatService adminChatService;
    private final NoticeService noticeService;
    private final EventCaller eventCaller;

    @Inject
    AdminChatCommand(AdminChatService adminChatService, NoticeService noticeService, EventCaller eventCaller) {
        this.adminChatService = adminChatService;
        this.noticeService = noticeService;
        this.eventCaller = eventCaller;
    }

    @Execute
    @DescriptionDocs(description = "Toggles persistent admin chat mode.", arguments = "<message>")
    void execute(@Context Player sender) {
        boolean persistent = this.adminChatService.toggleChatPersistent(sender.getUniqueId());

        this.noticeService.create()
            .notice(translation -> persistent ? translation.adminChat().enabledPersistentChat() : translation.adminChat().disabledPersistentChat())
            .player(sender.getUniqueId())
            .send();

    }

    @Execute
    @DescriptionDocs(description = "Sends a message to all staff members with eternalcore.adminchat.spy permissions", arguments = "<message>")
    void execute(@Context CommandSender sender, @Join String message) {
        AdminChatEvent event = this.eventCaller.callEvent(new AdminChatEvent(sender, message));

        if (event.isCancelled()) {
            return;
        }

        String eventMessage = event.getContent();

        this.adminChatService.sendAdminChatMessage(eventMessage, sender.getName());
    }
}
