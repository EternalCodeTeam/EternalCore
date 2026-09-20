package com.eternalcode.core.feature.punishment.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.punishment.PunishmentPermissions;
import com.eternalcode.core.feature.punishment.PunishmentService;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.ip.IpPunishmentService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.ip.IpAddressValidator;
import com.eternalcode.core.ip.PlayerIpResolver;
import com.eternalcode.core.notice.NoticeService;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.flag.Flag;
import dev.rollczi.litecommands.annotations.permission.Permission;

import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "unban")
@Permission("eternalcore.unban")
class UnbanCommand {

    private final PunishmentService punishmentService;
    private final IpPunishmentService ipPunishmentService;
    private final PlayerIpResolver playerIpResolver;
    private final NoticeService noticeService;
    private final PunishmentBroadcastService broadcastService;
    private final Server server;
    private final Logger logger;

    @Inject
    UnbanCommand(
        PunishmentService punishmentService,
        IpPunishmentService ipPunishmentService,
        PlayerIpResolver playerIpResolver,
        NoticeService noticeService,
        PunishmentBroadcastService broadcastService,
        Server server,
        Logger logger
    ) {
        this.punishmentService = punishmentService;
        this.ipPunishmentService = ipPunishmentService;
        this.playerIpResolver = playerIpResolver;
        this.noticeService = noticeService;
        this.broadcastService = broadcastService;
        this.server = server;
        this.logger = logger;
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Unban a player or a raw IP address", arguments = "<player|ip>")
    void execute(@Sender CommandSender operator, @Flag("-s") boolean silent, @Arg String targetOrIp) {
        if (IpAddressValidator.isValidIp(targetOrIp)) {
            this.unbanIp(operator, targetOrIp, silent);
            return;
        }

        this.unbanPlayer(operator, this.server.getOfflinePlayer(targetOrIp), silent);
    }

    private void unbanPlayer(CommandSender operator, OfflinePlayer target, boolean silent) {
        if (!this.punishmentService.isBanned(target.getUniqueId())) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().unbanNotBanned())
                .placeholder("{PLAYER}", target.getName())
                .sender(operator)
                .send();
            return;
        }

        PunishmentTarget operatorTarget = PunishmentTarget.of(operator);

        try {
            this.punishmentService.unban(PunishmentTarget.of(target), operatorTarget);
            this.onPlayerUnbanSuccess(operator, target, silent);
        }
        catch (Exception exception) {
            this.onFailure(operator, "unban", exception);
        }

        this.unbanLinkedIpIfNeeded(target, operatorTarget);
    }

    private void unbanIp(CommandSender operator, String ip, boolean silent) {
        if (!this.ipPunishmentService.isIpBanned(ip)) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().unbanIpNotBanned())
                .placeholder("{IP}", ip)
                .sender(operator)
                .send();
            return;
        }

        try {
            this.ipPunishmentService.unbanIp(ip, PunishmentTarget.of(operator));
            this.onIpUnbanSuccess(operator, ip, silent);
        }
        catch (Exception exception) {
            this.onFailure(operator, "unbanIp", exception);
        }
    }

    private void unbanLinkedIpIfNeeded(OfflinePlayer target, PunishmentTarget operatorTarget) {
        Optional<String> ipOptional = this.playerIpResolver.resolve(target);

        if (ipOptional.isEmpty()) {
            return;
        }

        String ip = ipOptional.get();

        if (!this.ipPunishmentService.isIpBanned(ip)) {
            return;
        }

        try {
            this.ipPunishmentService.unbanIp(ip, operatorTarget);
        }
        catch (Exception exception) {
            this.logger.log(Level.SEVERE, "Failed to lift linked IP ban for " + target.getName(), exception);
        }
    }

    private void onPlayerUnbanSuccess(CommandSender operator, OfflinePlayer target, boolean silent) {
        this.broadcastService.broadcast(
            translation -> silent ? translation.punishment().unbanBroadcastSilent() : translation.punishment().unbanBroadcast(),
            Map.of(
                "{PLAYER}", target.getName(),
                "{OPERATOR}", operator.getName()
            ),
            silent,
            PunishmentPermissions.STAFF_MESSAGES
        );

        this.broadcastService.privateConfirmation(
            translation -> translation.punishment().unbanSuccessPrivate(),
            Map.of("{PLAYER}", target.getName()),
            operator
        );
    }

    private void onIpUnbanSuccess(CommandSender operator, String ip, boolean silent) {
        this.broadcastService.broadcast(
            translation -> silent ? translation.punishment().unbanIpBroadcastSilent() : translation.punishment().unbanIpBroadcast(),
            Map.of(
                "{IP}", ip,
                "{OPERATOR}", operator.getName()
            ),
            silent,
            PunishmentPermissions.STAFF_MESSAGES
        );

        this.broadcastService.privateConfirmation(
            translation -> translation.punishment().unbanIpSuccessPrivate(),
            Map.of("{IP}", ip),
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
