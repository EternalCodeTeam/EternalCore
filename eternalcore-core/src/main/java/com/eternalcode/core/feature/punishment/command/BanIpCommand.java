package com.eternalcode.core.feature.punishment.command;

import static com.eternalcode.core.feature.punishment.PunishmentPermissions.BAN_IP_BYPASS;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.feature.punishment.DurationReasonParser;
import com.eternalcode.core.feature.punishment.PunishmentPermissions;
import com.eternalcode.core.feature.punishment.PunishmentReasonValidator;
import com.eternalcode.core.feature.punishment.PunishmentService;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.TemplateMessageRenderer;
import com.eternalcode.core.feature.punishment.ip.IpPunishmentService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.ip.PlayerIpResolver;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.flag.Flag;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;

import net.kyori.adventure.text.Component;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
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

    private final PunishmentService punishmentService;
    private final IpPunishmentService ipPunishmentService;
    private final PlayerIpResolver playerIpResolver;
    private final PunishmentSettings punishmentSettings;
    private final NoticeService noticeService;
    private final PunishmentReasonValidator reasonValidator;
    private final TemplateMessageRenderer templateRenderer;
    private final Logger logger;

    @Inject
    BanIpCommand(
        PunishmentService punishmentService,
        IpPunishmentService ipPunishmentService,
        PlayerIpResolver playerIpResolver,
        PunishmentSettings punishmentSettings,
        NoticeService noticeService,
        PunishmentReasonValidator reasonValidator,
        TemplateMessageRenderer templateRenderer,
        Logger logger
    ) {
        this.punishmentService = punishmentService;
        this.ipPunishmentService = ipPunishmentService;
        this.playerIpResolver = playerIpResolver;
        this.punishmentSettings = punishmentSettings;
        this.noticeService = noticeService;
        this.reasonValidator = reasonValidator;
        this.templateRenderer = templateRenderer;
        this.logger = logger;
    }

    @Execute
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

        this.playerIpResolver.resolve(target)
            .thenAccept(ipOptional -> {
                if (ipOptional.isEmpty()) {
                    this.noticeService.create()
                        .notice(translation -> translation.punishment().banIpNoAddress())
                        .placeholder("{PLAYER}", target.getName())
                        .sender(operator)
                        .send();
                    return;
                }

                this.finishBanIp(operator, target, duration, reason, silent, ipOptional.get());
            })
            .exceptionally(throwable -> this.onFailure(operator, "resolveIp", throwable));
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

        PunishmentTarget targetPunishmentTarget = PunishmentTarget.of(target);
        PunishmentTarget operatorPunishmentTarget = PunishmentTarget.of(operator);

        CompletableFuture<?> banFuture = this.punishmentService.ban(targetPunishmentTarget, operatorPunishmentTarget, reason, expiresAt, kickMessage);
        CompletableFuture<?> banIpFuture = this.ipPunishmentService.banIp(ip, targetPunishmentTarget, operatorPunishmentTarget, reason, expiresAt, kickMessage);

        CompletableFuture.allOf(banFuture, banIpFuture)
            .thenAccept(none -> this.onSuccess(operator, target, reason, expiresText, silent))
            .exceptionally(throwable -> this.onFailure(operator, "banIp", throwable));
    }

    private void onSuccess(CommandSender operator, OfflinePlayer target, String reason, String expiresText, boolean silent) {
        var broadcast = this.noticeService.create()
            .notice(translation -> silent
                ? translation.punishment().banBroadcastSilent()
                : translation.punishment().banBroadcast())
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
            .notice(translation -> translation.punishment().banIpSuccessPrivate())
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
