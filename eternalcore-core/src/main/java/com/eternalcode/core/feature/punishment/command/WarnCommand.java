package com.eternalcode.core.feature.punishment.command;

import static com.eternalcode.core.feature.punishment.PunishmentPermissions.WARN_BYPASS;

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

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.flag.Flag;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;

import java.time.Duration;
import java.time.Instant;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
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
    private final PunishmentBroadcastService broadcastService;
    private final PunishmentReasonValidator reasonValidator;
    private final Logger logger;

    @Inject
    WarnCommand(
        PunishmentService punishmentService,
        PunishmentSettings punishmentSettings,
        NoticeService noticeService,
        PunishmentBroadcastService broadcastService,
        PunishmentReasonValidator reasonValidator,
        Logger logger
    ) {
        this.punishmentService = punishmentService;
        this.punishmentSettings = punishmentSettings;
        this.noticeService = noticeService;
        this.broadcastService = broadcastService;
        this.reasonValidator = reasonValidator;
        this.logger = logger;
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Warn a player", arguments = "<player> <reason>")
    void execute(@Sender CommandSender operator, @Flag("-s") boolean silent, @Arg OfflinePlayer target, @Join String durationAndReason) {
        DurationReasonParser.Result parsed = DurationReasonParser.parse(durationAndReason);
        String reason = parsed.reason();

        if (!this.reasonValidator.isValid(parsed.reason())) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().warnInvalidReason())
                .placeholder("{MIN}", String.valueOf(this.punishmentSettings.minReasonLength()))
                .placeholder("{MAX}", String.valueOf(this.punishmentSettings.maxReasonLength()))
                .sender(operator)
                .send();
            return;
        }
        boolean isConsole = !(operator instanceof Player);

        if (!isConsole && target instanceof Player targetPlayer && targetPlayer.hasPermission(WARN_BYPASS)) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().warnCannotWarnAdmin())
                .placeholder("{PLAYER}", target.getName())
                .sender(operator)
                .send();
            return;
        }

        Duration duration = parsed.duration();
        Instant expiresAt = duration == null ? null : Instant.now().plus(duration);

        try {
            this.punishmentService.warn(PunishmentTarget.of(target), PunishmentTarget.of(operator), reason, expiresAt);
            this.onSuccess(operator, target, reason, silent);
        }
        catch (Exception exception) {
            this.onFailure(operator, "warn", exception);
        }
    }

    private void onSuccess(CommandSender operator, OfflinePlayer target, String reason, boolean silent) {
        this.broadcastService.broadcast(
            translation -> silent ? translation.punishment().warnBroadcastSilent() : translation.punishment().warnBroadcast(),
            Map.of(
                "{PLAYER}", target.getName(),
                "{OPERATOR}", operator.getName(),
                "{REASON}", reason
            ),
            silent,
            PunishmentPermissions.STAFF_MESSAGES
        );

        this.broadcastService.privateConfirmation(
            translation -> translation.punishment().warnSuccessPrivate(),
            Map.of("{PLAYER}", target.getName()),
            operator
        );
    }

    private void onFailure(CommandSender operator, String operation, Throwable throwable) {
        this.logger.log(Level.SEVERE, "Failed to execute punishment action (" + operation + ")", throwable);

        this.noticeService.create()
            .notice(translation -> translation.punishment().punishmentActionError())
            .sender(operator)
            .send();
    }
}
