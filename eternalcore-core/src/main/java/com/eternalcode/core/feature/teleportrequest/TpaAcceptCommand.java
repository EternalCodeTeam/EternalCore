package com.eternalcode.core.feature.teleportrequest;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Command(name = "tpaaccept", aliases = "tpaccept")
@Permission("eternalcore.tpaccept")
class TpaAcceptCommand {

    private final TeleportRequestService requestService;
    private final TeleportTaskService teleportTaskService;
    private final NoticeService noticeService;
    private final TeleportRequestSettings settings;
    private final Server server;

    @Inject
    TpaAcceptCommand(TeleportRequestService requestService, TeleportTaskService teleportTaskService, NoticeService noticeService, TeleportRequestSettings settings, Server server) {
        this.requestService = requestService;
        this.teleportTaskService = teleportTaskService;
        this.noticeService = noticeService;
        this.settings = settings;
        this.server = server;
    }

    @Execute
    @DescriptionDocs(description = "Accept teleport request", arguments = "[player]")
    void execute(@Sender Player player, @OptionalArg(RequesterArgument.KEY) Optional<Player> target) {
        if (!this.canAcceptTeleportRequest(player)) {
            this.sendTooLowMessage(player);
            return;
        }

        if (target.isEmpty()) {
            if (this.settings.requireExplicitNameInTpa()) {
                throw new InvalidUsageException(InvalidUsage.Cause.INVALID_ARGUMENT);
            }

            target = this.requestService
                .findLatestRequest(player.getUniqueId())
                .map(this.server::getPlayer);
        }

        if (target.isEmpty()) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.tpa().tpaAcceptNoRequestMessage());

            return;
        }

        this.acceptRequest(player, target.get());
    }

    @Execute(name = "-all", aliases = "*")
    @DescriptionDocs(description = "Accept all teleport requests")
    void executeAll(@Sender Player player) {
        if (!this.canAcceptTeleportRequest(player)) {
            this.sendTooLowMessage(player);

            return;
        }

        List<UUID> requests = this.requestService.findRequests(player.getUniqueId());

        if (requests.isEmpty()) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.tpa().tpaAcceptNoRequestMessage());

            return;
        }

        for (UUID uniqueId : requests) {
            Player requester = this.server.getPlayer(uniqueId);

            this.requestService.removeRequest(uniqueId);

            if (requester != null) {
                this.createTeleport(player, requester);
                this.sendRequesterNotification(player, requester);
            }
        }

        this.noticeService.player(player.getUniqueId(), translation -> translation.tpa().tpaAcceptAllAccepted());
    }

    private boolean canAcceptTeleportRequest(Player player) {
        return player.getLocation().getY() >= this.settings.minimumTpaAcceptY();
    }

    private void sendTooLowMessage(Player player) {
        this.noticeService
            .create()
            .player(player.getUniqueId())
            .notice(translation -> translation.tpa().tpaAcceptTooLowMessage())
            .placeholder("{Y}", String.valueOf(this.settings.minimumTpaAcceptY()))
            .send();
    }

    private void acceptRequest(Player player, Player requester) {
        this.createTeleport(player, requester);
        this.requestService.removeRequest(requester.getUniqueId());
        this.sendTeleportNotifications(player, requester);
    }

    private void createTeleport(Player player, Player requester) {
        this.teleportTaskService.createTeleport(
            requester.getUniqueId(),
            PositionAdapter.convert(requester.getLocation()),
            PositionAdapter.convert(player.getLocation()),
            this.settings.tpaTimer()
        );
    }

    private void sendTeleportNotifications(Player player, Player target) {
        this.noticeService
            .create()
            .player(player.getUniqueId())
            .notice(translation -> translation.tpa().tpaAcceptMessage())
            .placeholder("{PLAYER}", target.getName())
            .send();

        this.sendRequesterNotification(player, target);
    }

    private void sendRequesterNotification(Player player, Player requester) {
        this.noticeService
            .create()
            .player(requester.getUniqueId())
            .notice(translation -> translation.tpa().tpaAcceptReceivedMessage())
            .placeholder("{PLAYER}", player.getName())
            .send();
    }
}
