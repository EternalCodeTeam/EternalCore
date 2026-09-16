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

@Command(name = "unmute")
@Permission("eternalcore.unmute")
class UnmuteCommand {

    private final PunishmentService punishmentService;
    private final NoticeService noticeService;

    @Inject
    UnmuteCommand(PunishmentService punishmentService, NoticeService noticeService) {
        this.punishmentService = punishmentService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Unmute a player", arguments = "<player>")
    void execute(@Sender CommandSender operator, @Arg OfflinePlayer target) {
        if (!this.punishmentService.isMuted(target.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().unmuteNotMuted())
                .placeholder("{PLAYER}", target.getName())
                .sender(operator)
                .send();
            return;
        }

        this.punishmentService.unmute(PunishmentTarget.of(target), PunishmentTarget.of(operator));

        this.noticeService.create()
            .notice(translation -> translation.punishment().unmuteBroadcast())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{OPERATOR}", operator.getName())
            .all()
            .send();

        this.noticeService.create()
            .notice(translation -> translation.punishment().unmuteSuccessPrivate())
            .placeholder("{PLAYER}", target.getName())
            .sender(operator)
            .send();
    }
}
