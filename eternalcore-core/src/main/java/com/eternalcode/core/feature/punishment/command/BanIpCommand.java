package com.eternalcode.core.feature.punishment.ip.command;

import static com.eternalcode.core.feature.punishment.PunishmentPermissions.BAN_IP_BYPASS;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.annotations.scan.permission.PermissionDocs;
import com.eternalcode.core.feature.punishment.PunishmentPermissions;
import com.eternalcode.core.feature.punishment.PunishmentReasonValidator;
import com.eternalcode.core.feature.punishment.PunishmentService;
import com.eternalcode.core.feature.punishment.PunishmentSettings;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.TemplateMessageRenderer;
import com.eternalcode.core.feature.punishment.ip.IpPunishmentService;
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

import net.kyori.adventure.text.Component;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

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
    private final PunishmentSettings punishmentSettings;
    private final NoticeService noticeService;
    private final PunishmentReasonValidator reasonValidator;
    private final TemplateMessageRenderer templateRenderer;

    @Inject
    BanIpCommand(
        PunishmentService punishmentService,
        IpPunishmentService ipPunishmentService,
        PunishmentSettings punishmentSettings,
        NoticeService noticeService,
        PunishmentReasonValidator reasonValidator,
        TemplateMessageRenderer templateRenderer
    ) {
        this.punishmentService = punishmentService;
        this.ipPunishmentService = ipPunishmentService;
        this.punishmentSettings = punishmentSettings;
        this.noticeService = noticeService;
        this.reasonValidator = reasonValidator;
        this.templateRenderer = templateRenderer;
    }

    @Execute
    @DescriptionDocs(description = "Ban a player and their IP address permanently", arguments = "<player> <reason>")
    void executeBanIp(@Sender CommandSender operator, @Flag("-s") boolean silent, @Arg Player target, @Join String reason) {
        this.banIp(operator, target, null, reason, silent);
    }

    @Execute
    @DescriptionDocs(description = "Ban a player and their IP address for a specified duration", arguments = "<player> <time> <reason>")
    void executeBanIpFor(@Sender CommandSender operator, @Flag("-s") boolean silent, @Arg Player target, @Arg Duration duration, @Join String reason) {
        this.banIp(operator, target, duration, reason, silent);
    }

    private void banIp(CommandSender operator, Player target, Duration duration, String reason, boolean silent) {
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

        if (!isConsole && target.hasPermission(BAN_IP_BYPASS)) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().banCannotBanAdmin())
                .placeholder("{PLAYER}", target.getName())
                .sender(operator)
                .send();
            return;
        }

        InetSocketAddress socketAddress = target.getAddress();

        if (socketAddress == null || socketAddress.getAddress() == null) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().banIpNoAddress())
                .placeholder("{PLAYER}", target.getName())
                .sender(operator)
                .send();
            return;
        }

        String ip = socketAddress.getAddress().getHostAddress();
        Instant expiresAt = duration == null ? null : Instant.now().plus(duration);
        String expiresText = expiresAt == null ? this.punishmentSettings.permanentLabel() : DurationUtil.format(duration, true);

        List<Component> kickMessage = this.templateRenderer.render(
            this.punishmentSettings.banKickScreen(),
            Map.of(
                "{PLAYER}", target.getName(),
                "{OPERATOR}", operator.getName(),
                "{REASON}", reason,
                "{EXPIRES}", expiresText
            )
        );

        PunishmentTarget targetPunishmentTarget = PunishmentTarget.of(target);
        PunishmentTarget operatorPunishmentTarget = PunishmentTarget.of(operator);

        this.punishmentService.ban(targetPunishmentTarget, operatorPunishmentTarget, reason, expiresAt, kickMessage);
        this.ipPunishmentService.banIp(ip, targetPunishmentTarget, operatorPunishmentTarget, reason, expiresAt, kickMessage);

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
}
