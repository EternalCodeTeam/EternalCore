package com.eternalcode.core.feature.punishment.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.punishment.ip.IpPunishmentService;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;

import org.bukkit.command.CommandSender;

@Command(name = "unbanip")
@Permission("eternalcore.unbanip")
class UnbanIpCommand {

    private final IpPunishmentService ipPunishmentService;
    private final NoticeService noticeService;

    @Inject
    UnbanIpCommand(IpPunishmentService ipPunishmentService, NoticeService noticeService) {
        this.ipPunishmentService = ipPunishmentService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Unban an IP address", arguments = "<ip>")
    void execute(@Sender CommandSender operator, @Arg String ip) {
        if (!this.ipPunishmentService.isIpBanned(ip)) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().unbanIpNotBanned())
                .placeholder("{IP}", ip)
                .sender(operator)
                .send();
            return;
        }

        this.ipPunishmentService.unbanIp(ip, PunishmentTarget.of(operator));

        this.noticeService.create()
            .notice(translation -> translation.punishment().unbanIpBroadcast())
            .placeholder("{IP}", ip)
            .placeholder("{OPERATOR}", operator.getName())
            .all()
            .send();

        this.noticeService.create()
            .notice(translation -> translation.punishment().unbanIpSuccessPrivate())
            .placeholder("{IP}", ip)
            .sender(operator)
            .send();
    }
}
