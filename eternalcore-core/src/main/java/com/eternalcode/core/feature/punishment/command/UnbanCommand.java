package com.eternalcode.core.feature.punishment.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.punishment.PunishmentService;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

@Command(name = "unban")
@Permission("eternalcore.unban")
class UnbanCommand {

    private final PunishmentService punishmentService;
    private final NoticeService noticeService;

    @Inject
    UnbanCommand(PunishmentService punishmentService, NoticeService noticeService) {
        this.punishmentService = punishmentService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Unban a player", arguments = "<player>")
    void execute(@Sender CommandSender operator, @Arg OfflinePlayer target) {
        if (!this.punishmentService.isBanned(target.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().unbanNotBanned())
                .placeholder("{PLAYER}", target.getName())
                .sender(operator)
                .send();
            return;
        }

        this.punishmentService.unban(PunishmentTarget.of(target), PunishmentTarget.of(operator));

        this.noticeService.create()
            .notice(translation -> translation.punishment().unbanBroadcast())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{OPERATOR}", operator.getName())
            .all()
            .send();

        this.noticeService.create()
            .notice(translation -> translation.punishment().unbanSuccessPrivate())
            .placeholder("{PLAYER}", target.getName())
            .sender(operator)
            .send();
    }
}
