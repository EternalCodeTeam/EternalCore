package com.eternalcode.core.feature.punishment.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.punishment.PunishmentPermissions;
import com.eternalcode.core.feature.punishment.PunishmentService;
import com.eternalcode.core.feature.punishment.PunishmentTarget;
import com.eternalcode.core.feature.punishment.ip.IpPunishment;
import com.eternalcode.core.feature.punishment.ip.IpPunishmentService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.ip.IpAddressValidator;
import com.eternalcode.core.ip.PlayerIpResolver;
import com.eternalcode.core.litecommand.argument.KnownPlayerResolver;
import com.eternalcode.core.notice.NoticeService;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.flag.Flag;
import dev.rollczi.litecommands.annotations.permission.Permission;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Command(name = "unban")
@Permission("eternalcore.unban")
class UnbanCommand {

    private static final String PLAYER_PLACEHOLDER = "{PLAYER}";
    private static final String OPERATOR_PLACEHOLDER = "{OPERATOR}";
    private static final String OWNER_PLACEHOLDER = "{OWNER}";
    private static final String IP_PLACEHOLDER = "{IP}";

    private final PunishmentService punishmentService;
    private final IpPunishmentService ipPunishmentService;
    private final PlayerIpResolver playerIpResolver;
    private final KnownPlayerResolver knownPlayerResolver;
    private final NoticeService noticeService;
    private final PunishmentBroadcastService broadcastService;
    private final Logger logger;

    @Inject
    UnbanCommand(
        PunishmentService punishmentService,
        IpPunishmentService ipPunishmentService,
        PlayerIpResolver playerIpResolver,
        KnownPlayerResolver knownPlayerResolver,
        NoticeService noticeService,
        PunishmentBroadcastService broadcastService,
        Logger logger
    ) {
        this.punishmentService = punishmentService;
        this.ipPunishmentService = ipPunishmentService;
        this.playerIpResolver = playerIpResolver;
        this.knownPlayerResolver = knownPlayerResolver;
        this.noticeService = noticeService;
        this.broadcastService = broadcastService;
        this.logger = logger;
    }

    @Execute
    @Async
    @DescriptionDocs(description = "Unban a player (including their own IP ban) or a raw IP address", arguments = "<player|ip>")
    void execute(@Sender CommandSender operator, @Flag("-s") boolean silent, @Arg String targetOrIp) {
        if (IpAddressValidator.isValidIp(targetOrIp)) {
            this.unbanIp(operator, targetOrIp, silent);
            return;
        }

        this.knownPlayerResolver.resolve(targetOrIp).ifPresentOrElse(
            target -> this.unbanPlayer(operator, target, silent),
            () -> this.sendPlayerNotFound(operator)
        );
    }

    private void unbanPlayer(CommandSender operator, OfflinePlayer target, boolean silent) {
        PunishmentTarget punishmentTarget = PunishmentTarget.of(target);
        UUID targetUuid = punishmentTarget.uuid();
        String targetName = punishmentTarget.name();

        boolean playerBanned = this.punishmentService.isBanned(targetUuid);
        Optional<IpPunishment> ipBan = this.findIpBan(target);
        Optional<IpPunishment> ownIpBan = ipBan.filter(ban -> this.isIssuedFor(ban, targetUuid));
        Optional<IpPunishment> foreignIpBan = ipBan.filter(ban -> !this.isIssuedFor(ban, targetUuid));

        if (!playerBanned && ownIpBan.isEmpty()) {
            foreignIpBan.ifPresentOrElse(
                ban -> this.sendIpBannedForOther(operator, targetName, ban),
                () -> this.sendNotBanned(operator, targetName)
            );
            return;
        }

        PunishmentTarget operatorTarget = PunishmentTarget.of(operator);

        try {
            if (playerBanned) {
                this.punishmentService.unban(punishmentTarget, operatorTarget);
            }

            ownIpBan.ifPresent(ipPunishment -> this.ipPunishmentService.unbanIp(ipPunishment.ip(), operatorTarget));

            this.onPlayerUnbanSuccess(operator, targetName, silent);
        }
        catch (Exception exception) {
            this.onFailure(operator, "unban", exception);
            return;
        }

        foreignIpBan.ifPresent(ban -> this.sendIpBannedForOther(operator, targetName, ban));
    }

    private Optional<IpPunishment> findIpBan(OfflinePlayer target) {
        return this.playerIpResolver.resolve(target)
            .flatMap(this.ipPunishmentService::getActiveIpBan);
    }

    private boolean isIssuedFor(IpPunishment ipBan, UUID targetUuid) {
        return ipBan.target().uuid().equals(targetUuid);
    }

    private void unbanIp(CommandSender operator, String ip, boolean silent) {
        if (!this.ipPunishmentService.isIpBanned(ip)) {
            this.noticeService.create()
                .notice(translation -> translation.punishment().unbanIpNotBanned())
                .placeholder(IP_PLACEHOLDER, ip)
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

    private void sendPlayerNotFound(CommandSender operator) {
        this.noticeService.create()
            .notice(translation -> translation.argument().missingPlayer())
            .sender(operator)
            .send();
    }

    private void sendNotBanned(CommandSender operator, String targetName) {
        this.noticeService.create()
            .notice(translation -> translation.punishment().unbanNotBanned())
            .placeholder(PLAYER_PLACEHOLDER, targetName)
            .sender(operator)
            .send();
    }

    private void sendIpBannedForOther(CommandSender operator, String targetName, IpPunishment ipBan) {
        this.noticeService.create()
            .notice(translation -> translation.punishment().unbanIpBannedForOther())
            .placeholder(PLAYER_PLACEHOLDER, targetName)
            .placeholder(OWNER_PLACEHOLDER, ipBan.target().name())
            .placeholder(IP_PLACEHOLDER, ipBan.ip())
            .sender(operator)
            .send();
    }

    private void onPlayerUnbanSuccess(CommandSender operator, String targetName, boolean silent) {
        this.broadcastService.broadcast(
            translation -> silent ? translation.punishment().unbanBroadcastSilent() : translation.punishment().unbanBroadcast(),
            Map.of(
                PLAYER_PLACEHOLDER, targetName,
                OPERATOR_PLACEHOLDER, operator.getName()
            ),
            silent,
            PunishmentPermissions.STAFF_MESSAGES
        );

        this.broadcastService.privateConfirmation(
            translation -> translation.punishment().unbanSuccessPrivate(),
            Map.of(PLAYER_PLACEHOLDER, targetName),
            operator
        );
    }

    private void onIpUnbanSuccess(CommandSender operator, String ip, boolean silent) {
        this.broadcastService.broadcast(
            translation -> silent ? translation.punishment().unbanIpBroadcastSilent() : translation.punishment().unbanIpBroadcast(),
            Map.of(
                IP_PLACEHOLDER, ip,
                OPERATOR_PLACEHOLDER, operator.getName()
            ),
            silent,
            PunishmentPermissions.STAFF_MESSAGES
        );

        this.broadcastService.privateConfirmation(
            translation -> translation.punishment().unbanIpSuccessPrivate(),
            Map.of(IP_PLACEHOLDER, ip),
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
