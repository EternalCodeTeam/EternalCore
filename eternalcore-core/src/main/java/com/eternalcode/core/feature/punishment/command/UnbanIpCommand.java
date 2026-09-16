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

import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "unbanip")
@Permission("eternalcore.unbanip")
class UnbanIpCommand {

    private final IpPunishmentService ipPunishmentService;
    private final NoticeService noticeService;
    private final Logger logger;

    @Inject
    UnbanIpCommand(IpPunishmentService ipPunishmentService, NoticeService noticeService, Logger logger) {
        this.ipPunishmentService = ipPunishmentService;
        this.noticeService = noticeService;
        this.logger = logger;
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

        this.ipPunishmentService.unbanIp(ip, PunishmentTarget.of(operator))
            .thenAccept(none -> this.onSuccess(operator, ip))
            .exceptionally(throwable -> {
                this.logger.log(Level.SEVERE, "Failed to execute punishment action (unbanIp)", throwable);

                this.noticeService.create()
                    .notice(translation -> translation.punishment().punishmentActionError())
                    .sender(operator)
                    .send();

                return null;
            });
    }

    private void onSuccess(CommandSender operator, String ip) {
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
