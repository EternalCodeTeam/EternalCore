package com.eternalcode.core.feature.punishment.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.punishment.PunishmentService;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.ip.IpPunishmentService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.ip.PlayerIpResolver;
import com.eternalcode.core.notice.NoticeService;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "unban")
@Permission("eternalcore.unban")
class UnbanCommand {

    private final PunishmentService punishmentService;
    private final IpPunishmentService ipPunishmentService;
    private final PlayerIpResolver playerIpResolver;
    private final NoticeService noticeService;
    private final Logger logger;

    @Inject
    UnbanCommand(
        PunishmentService punishmentService,
        IpPunishmentService ipPunishmentService,
        PlayerIpResolver playerIpResolver,
        NoticeService noticeService,
        Logger logger
    ) {
        this.punishmentService = punishmentService;
        this.ipPunishmentService = ipPunishmentService;
        this.playerIpResolver = playerIpResolver;
        this.noticeService = noticeService;
        this.logger = logger;
    }

    @Execute
    @DescriptionDocs(description = "Unban a player (also lifts any IP ban tied to their last known address)", arguments = "<player>")
    void execute(@Sender CommandSender operator, @Arg OfflinePlayer target) {
        if (!this.punishmentService.isBanned(target.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().unbanNotBanned())
                .placeholder("{PLAYER}", target.getName())
                .sender(operator)
                .send();
            return;
        }

        PunishmentTarget operatorTarget = PunishmentTarget.of(operator);

        this.punishmentService.unban(PunishmentTarget.of(target), operatorTarget)
            .thenAccept(none -> this.onSuccess(operator, target))
            .exceptionally(throwable -> this.onFailure(operator, "unban", throwable));

        this.unbanLinkedIpIfNeeded(target, operatorTarget);
    }

    private void onSuccess(CommandSender operator, OfflinePlayer target) {
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

    private void unbanLinkedIpIfNeeded(OfflinePlayer target, PunishmentTarget operatorTarget) {
        this.playerIpResolver.resolve(target)
            .thenAccept(ipOptional -> {
                if (ipOptional.isEmpty()) {
                    return;
                }

                String ip = ipOptional.get();

                if (this.ipPunishmentService.isIpBanned(ip)) {
                    this.ipPunishmentService.unbanIp(ip, operatorTarget)
                        .exceptionally(throwable -> {
                            this.logger.log(Level.SEVERE, "Failed to lift linked IP ban for " + target.getName(), throwable);
                            return null;
                        });
                }
            })
            .exceptionally(throwable -> {
                this.logger.log(Level.SEVERE, "Failed to resolve IP for " + target.getName() + " during unban", throwable);
                return null;
            });
    }

    private Void onFailure(CommandSender operator, String operation, Throwable throwable) {
        this.logger.log(Level.SEVERE, "Failed to execute punishment action (" + operation + ")", throwable);

        this.noticeService.create()
            .notice(translation -> translation.punishment().punishmentActionError())
            .sender(operator)
            .send();

        return null;
    }
}
