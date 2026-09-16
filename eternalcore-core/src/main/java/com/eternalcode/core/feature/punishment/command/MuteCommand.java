package com.eternalcode.core.feature.punishment.command;

import static com.eternalcode.core.feature.punishment.PunishmentPermissions.MUTE_BYPASS;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.feature.punishment.DurationReasonParser;
import com.eternalcode.core.feature.punishment.PunishmentPermissions;
import com.eternalcode.core.feature.punishment.PunishmentReasonValidator;
import com.eternalcode.core.feature.punishment.PunishmentService;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;

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

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "mute")
@Permission("eternalcore.mute")
@PermissionDocs(
    name = "Mute Bypass",
    permission = MUTE_BYPASS,
    description = "Permission allows to bypass being muted"
)
class MuteCommand {

    private final PunishmentService punishmentService;
    private final PunishmentSettings punishmentSettings;
    private final NoticeService noticeService;
    private final PunishmentReasonValidator reasonValidator;
    private final Logger logger;

    @Inject
    MuteCommand(
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
    @DescriptionDocs(description = "Mute a player, optionally for a specified duration", arguments = "<player> [time] <reason>")
    void executeMute(@Sender CommandSender operator, @Flag("-s") boolean silent, @Arg OfflinePlayer target, @Join String durationAndReason) {
        DurationReasonParser.Result parsed = DurationReasonParser.parse(durationAndReason);
        this.mute(operator, target, parsed.duration(), parsed.reason(), silent);
    }

    private void mute(CommandSender operator, OfflinePlayer target, Duration duration, String reason, boolean silent) {
        if (!this.reasonValidator.isValid(reason)) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().muteInvalidReason())
                .placeholder("{MIN}", String.valueOf(this.punishmentSettings.minReasonLength()))
                .placeholder("{MAX}", String.valueOf(this.punishmentSettings.maxReasonLength()))
                .sender(operator)
                .send();
            return;
        }

        if (this.punishmentService.isMuted(target.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().muteAlreadyMuted())
                .placeholder("{PLAYER}", target.getName())
                .sender(operator)
                .send();
            return;
        }

        if (target instanceof Player targetPlayer && targetPlayer.hasPermission(MUTE_BYPASS)) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().muteCannotMuteAdmin())
                .placeholder("{PLAYER}", target.getName())
                .sender(operator)
                .send();
            return;
        }

        Instant expiresAt = duration == null ? null : Instant.now().plus(duration);
        String expiresText = expiresAt == null ? this.punishmentSettings.permanentLabel() : DurationUtil.format(duration, true);

        this.punishmentService.mute(
                PunishmentTarget.of(target),
                PunishmentTarget.of(operator),
                reason,
                expiresAt
            )
            .thenAccept(punishment -> this.onSuccess(operator, target, reason, expiresText, silent))
            .exceptionally(throwable -> this.onFailure(operator, "mute", throwable));
    }

    private void onSuccess(CommandSender operator, OfflinePlayer target, String reason, String expiresText, boolean silent) {
        var broadcast = this.noticeService.create()
            .notice(translation -> silent
                ? translation.punishment().muteBroadcastSilent()
                : translation.punishment().muteBroadcast())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{OPERATOR}", operator.getName())
            .placeholder("{REASON}", reason)
            .placeholder("{EXPIRES}", expiresText);

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
            .notice(translation -> translation.punishment().muteSuccessPrivate())
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
