package com.eternalcode.core.feature.adminchat;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

@Command(name = "adminchat", aliases = {"ac"})
@Permission(AdminChatPermissionConstant.ADMIN_CHAT_PERMISSION)
final class AdminChatCommand {

    private final AdminChatService adminChatService;
    private final NoticeService noticeService;

    @Inject
    AdminChatCommand(@NonNull AdminChatService adminChatService, @NonNull NoticeService noticeService) {
        this.adminChatService = adminChatService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(
        description = "Toggles persistent admin chat mode. When enabled, all your messages will be sent to admin chat."
    )
    void executeToggle(@Context @NonNull Player sender) {
        boolean enabled = this.adminChatService.toggleChat(sender.getUniqueId());

        this.noticeService.create()
            .notice(translation -> enabled
                ? translation.adminChat().enabled()
                : translation.adminChat().disabled())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute
    @DescriptionDocs(
        description = "Sends a message to all staff members with admin chat permissions.",
        arguments = "<message>"
    )
    void executeSendMessage(@Context @NonNull CommandSender sender, @Join @NonNull String message) {
        if (message.trim().isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.argument().noArgument())
                .sender(sender)
                .send();
            return;
        }

        this.adminChatService.sendAdminChatMessage(message, sender);
    }
}
