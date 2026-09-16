package com.eternalcode.core.feature.punishment.command;

import static com.eternalcode.core.feature.punishment.PunishmentPermissions.WARN_BYPASS;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.feature.punishment.PunishmentPermissions;
import com.eternalcode.core.feature.punishment.PunishmentReasonValidator;
import com.eternalcode.core.feature.punishment.PunishmentService;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.flag.Flag;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "warn")
@Permission("eternalcore.warn")
@PermissionDocs(
    name = "Warn Bypass",
    permission = WARN_BYPASS,
    description = "Permission allows to bypass being warned"
)
class WarnCommand {

    private final PunishmentService punishmentService;
    private final PunishmentSettings punishmentSettings;
    private final NoticeService noticeService;
    private final PunishmentReasonValidator reasonValidator;
    private final Logger logger;

    @Inject
    WarnCommand(
        PunishmentService punishmentService,
        PunishmentSettings punishmentSettings,
        NoticeService noticeService,
        PunishmentReasonValidator reasonValidator,
        Logger logger
    ) {
        this.punishmentService = punishmentService;
        this.punishmentSettings = punishmentSettings;
        this.noticeService = noticeService;
        this.reasonValidator = reasonValidator;
        this.logger = logger;
    }

    @Execute
    @DescriptionDocs(description = "Warn a player", arguments = "<player> <reason>")
    void execute(@Sender CommandSender operator, @Flag("-s") boolean silent, @Arg OfflinePlayer target, @Join String reason) {
        if (!this.reasonValidator.isValid(reason)) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().warnInvalidReason())
                .placeholder("{MIN}", String.valueOf(this.punishmentSettings.minReasonLength()))
                .placeholder("{MAX}", String.valueOf(this.punishmentSettings.maxReasonLength()))
                .sender(operator)
                .send();
            return;
        }

        if (target instanceof Player targetPlayer && targetPlayer.hasPermission(WARN_BYPASS)) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().warnCannotWarnAdmin())
                .placeholder("{PLAYER}", target.getName())
                .sender(operator)
                .send();
            return;
        }

        this.punishmentService.warn(
                PunishmentTarget.of(target),
                PunishmentTarget.of(operator),
                reason
            )
            .thenAccept(punishment -> this.onSuccess(operator, target, reason, silent))
            .exceptionally(throwable -> this.onFailure(operator, "warn", throwable));
    }

    private void onSuccess(CommandSender operator, OfflinePlayer target, String reason, boolean silent) {
        var broadcast = this.noticeService.create()
            .notice(translation -> silent
                ? translation.punishment().warnBroadcastSilent()
                : translation.punishment().warnBroadcast())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{OPERATOR}", operator.getName())
            .placeholder("{REASON}", reason);

        if (silent) {
            for (Player staff : operator.getServer().getOnlinePlayers()) {
                if (staff.hasPermission(PunishmentPermissions.STAFF_MESSAGES)) {
                    broadcast = broadcast.player(staff.getUniqueId());
                }
            }
        }
        else {
            broadcast = broadcast.all();
        }

        broadcast.send();

        this.noticeService.create()
            .notice(translation -> translation.punishment().warnSuccessPrivate())
            .placeholder("{PLAYER}", target.getName())
            .sender(operator)
            .send();
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
