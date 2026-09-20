package com.eternalcode.core.feature.punishment.command;

import static com.eternalcode.core.feature.punishment.PunishmentPermissions.BAN_IP_BYPASS;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.feature.punishment.DurationReasonParser;
import com.eternalcode.core.feature.punishment.PunishmentPermissions;
import com.eternalcode.core.feature.punishment.PunishmentReasonValidator;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.TemplateMessageRenderer;
import com.eternalcode.core.feature.punishment.ip.IpPunishmentService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.ip.PlayerIpResolver;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.flag.Flag;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;

import net.kyori.adventure.text.Component;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "banip")
@Permission("eternalcore.banip")
@PermissionDocs(
    name = "Ban IP Bypass",
    permission = BAN_IP_BYPASS,
    description = "Permission allows to bypass being IP-banned"
)
class BanIpCommand {

    private final IpPunishmentService ipPunishmentService;
    private final PlayerIpResolver playerIpResolver;
    private final PunishmentSettings punishmentSettings;
    private final NoticeService noticeService;
    private final PunishmentBroadcastService broadcastService;
    private final PunishmentReasonValidator reasonValidator;
    private final TemplateMessageRenderer templateRenderer;
    private final Logger logger;

    @Inject
    BanIpCommand(
        IpPunishmentService ipPunishmentService,
        PlayerIpResolver playerIpResolver,
        PunishmentSettings punishmentSettings,
        NoticeService noticeService,
        PunishmentBroadcastService broadcastService,
        PunishmentReasonValidator reasonValidator,
        TemplateMessageRenderer templateRenderer,
        Logger logger
    ) {
        this.ipPunishmentService = ipPunishmentService;
        this.playerIpResolver = playerIpResolver;
        this.punishmentSettings = punishmentSettings;
        this.noticeService = noticeService;
        this.broadcastService = broadcastService;
        this.reasonValidator = reasonValidator;
        this.templateRenderer = templateRenderer;
        this.logger = logger;
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Ban a player and their IP address, optionally for a specified duration", arguments = "<player> [time] <reason>")
    void executeBanIp(@Sender CommandSender operator, @Flag("-s") boolean silent, @Arg OfflinePlayer target, @Join String durationAndReason) {
        DurationReasonParser.Result parsed = DurationReasonParser.parse(durationAndReason);
        this.banIp(operator, target, parsed.duration(), parsed.reason(), silent);
    }

    private void banIp(CommandSender operator, OfflinePlayer target, Duration duration, String reason, boolean silent) {
        if (!this.reasonValidator.isValid(reason)) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().banInvalidReason())
                .placeholder("{MIN}", String.valueOf(this.punishmentSettings.minReasonLength()))
                .placeholder("{MAX}", String.valueOf(this.punishmentSettings.maxReasonLength()))
                .sender(operator)
                .send();
            return;
        }
        boolean isConsole = !(operator instanceof Player);

        if (!isConsole && target instanceof Player targetPlayer && targetPlayer.hasPermission(BAN_IP_BYPASS)) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().banCannotBanAdmin())
                .placeholder("{PLAYER}", target.getName())
                .sender(operator)
                .send();
            return;
        }

        Optional<String> ipOptional = this.playerIpResolver.resolve(target);

        if (ipOptional.isEmpty()) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().banIpNoAddress())
                .placeholder("{PLAYER}", target.getName())
                .sender(operator)
                .send();
            return;
        }

        this.finishBanIp(operator, target, duration, reason, silent, ipOptional.get());
    }

    private void finishBanIp(CommandSender operator, OfflinePlayer target, Duration duration, String reason, boolean silent, String ip) {
        Instant expiresAt = duration == null ? null : Instant.now().plus(duration);
        String expiresText = expiresAt == null ? this.punishmentSettings.permanentLabel() : DurationUtil.format(duration, true);

        List<Component> kickMessage = this.templateRenderer.render(
            this.punishmentSettings.banIpKickScreen(),
            Map.of(
                "{PLAYER}", target.getName(),
                "{OPERATOR}", operator.getName(),
                "{REASON}", reason,
                "{EXPIRES}", expiresText
            )
        );

        try {
            this.ipPunishmentService.banIp(
                ip,
                PunishmentTarget.of(target),
                PunishmentTarget.of(operator),
                reason,
                expiresAt,
                kickMessage
            );

            this.onSuccess(operator, target, reason, expiresText, silent);
        }
        catch (Exception exception) {
            this.onFailure(operator, "banIp", exception);
        }
    }

    private void onSuccess(CommandSender operator, OfflinePlayer target, String reason, String expiresText, boolean silent) {
        this.broadcastService.broadcast(
            translation -> silent ? translation.punishment().banBroadcastSilent() : translation.punishment().banBroadcast(),
            Map.of(
                "{PLAYER}", target.getName(),
                "{OPERATOR}", operator.getName(),
                "{REASON}", reason,
                "{EXPIRES}", expiresText
            ),
            silent,
            PunishmentPermissions.STAFF_MESSAGES
        );

        this.broadcastService.privateConfirmation(
            translation -> translation.punishment().banIpSuccessPrivate(),
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
