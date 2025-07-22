package com.eternalcode.core.feature.adminchat;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.adminchat.event.AdminChatService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "adminchat", aliases = "ac")
@Permission("eternalcore.adminchat")
class AdminChatCommand {
    private final AdminChatService adminChatService;
    private final NoticeService noticeService;

    @Inject
    AdminChatCommand(AdminChatService adminChatService, NoticeService noticeService) {
        this.adminChatService = adminChatService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Toggles persistent admin chat mode.", arguments = "<message>")
    void execute(@Context Player sender) {
        boolean enabled = this.adminChatService.toggleChat(sender.getUniqueId());

        this.noticeService.create()
            .notice(translation -> enabled ? translation.adminChat().enabled() : translation.adminChat().disabled())
            .player(sender.getUniqueId())
            .send();

    }

    @Execute
    @DescriptionDocs(description = "Sends a message to all staff members with eternalcore.adminchat.spy permissions", arguments = "<message>")
    void execute(@Context CommandSender sender, @Join String message) {
        this.adminChatService.sendAdminChatMessage(message, sender);
    }
}
