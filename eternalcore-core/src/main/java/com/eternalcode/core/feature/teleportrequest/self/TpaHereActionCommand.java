package com.eternalcode.core.feature.teleportrequest.self;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.teleport.TeleportTaskService;
import com.eternalcode.core.feature.teleportrequest.TeleportRequestSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
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

@RootCommand
class TpaHereActionCommand {

    private final TeleportHereRequestService requestService;
    private final TeleportTaskService teleportTaskService;
    private final TeleportRequestSettings settings;
    private final NoticeService noticeService;
    private final Server server;

    @Inject
    TpaHereActionCommand(TeleportHereRequestService requestService, TeleportTaskService teleportTaskService, TeleportRequestSettings settings, NoticeService noticeService, Server server) {
        this.requestService = requestService;
        this.teleportTaskService = teleportTaskService;
        this.settings = settings;
        this.noticeService = noticeService;
        this.server = server;
    }

    @Execute(name = "tpahereaccept")
    @Permission("eternalcore.tpaccept")
    @DescriptionDocs(description = "Accept teleport here request", arguments = "[player]")
    void accept(@Sender Player player, @OptionalArg(SelfRequesterArgument.KEY) Optional<Player> target) {
        if (target.isEmpty()) {
            if (this.settings.requireExplicitNameInTpa()) {
                throw new InvalidUsageException(InvalidUsage.Cause.INVALID_ARGUMENT);
            }

            target = this.requestService.findLatestRequest(player.getUniqueId())
                .map(this.server::getPlayer);
        }

        if (target.isEmpty()) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.tpa().tpaAcceptNoRequestMessage());

            return;
        }

        this.acceptRequest(player, target.get());
    }

    @Execute(name = "tpaheredeny")
    @Permission("eternalcore.tpahere.deny")
    @DescriptionDocs(description = "Deny a teleport here request")
    void executeTarget(@Sender Player player, @Arg(SelfRequesterArgument.KEY) Player target) {
        UUID playerId = player.getUniqueId();
        UUID targetId = target.getUniqueId();

        this.requestService.removeRequest(targetId);

        this.noticeService
            .create()
            .player(playerId)
            .notice(translation -> translation.tpa().tpaDenyDoneMessage())
            .placeholder("{PLAYER}", target.getName())
            .send();

        this.noticeService
            .create()
            .player(targetId)
            .notice(translation -> translation.tpa().tpaDenyReceivedMessage())
            .placeholder("{PLAYER}", player.getName())
            .send();
    }

    @Execute(name = "tpaheredeny -all")
    @Permission("eternalcore.tpahere.deny")
    @DescriptionDocs(description = "Deny all teleport here requests")
    void executeAll(@Sender Player player) {
        UUID playerId = player.getUniqueId();
        List<UUID> requests = this.requestService.findRequests(playerId);

        if (requests.isEmpty()) {
            this.noticeService.player(playerId, translation -> translation.tpa().tpaDenyNoRequestMessage());
            return;
        }

        for (UUID uniqueId : requests) {
            Player requester = this.server.getPlayer(uniqueId);
            this.requestService.removeRequest(uniqueId);

            if (requester != null) {
                this.sendRequesterNotification(player, requester);
            }
        }

        this.noticeService.player(playerId, translation -> translation.tpa().tpaDenyAllDenied());
    }

    private void acceptRequest(Player player, Player requester) {
        this.createTeleport(player, requester);
        this.requestService.removeRequest(requester.getUniqueId());
        this.sendTeleportNotifications(player, requester);
    }

    private void createTeleport(Player player, Player requester) {
        this.teleportTaskService.createTeleport(
            player.getUniqueId(),
            PositionAdapter.convert(player.getLocation()),
            PositionAdapter.convert(requester.getLocation()),
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

        this.noticeService
            .create()
            .player(target.getUniqueId())
            .notice(translation -> translation.tpa().tpaAcceptReceivedMessage())
            .placeholder("{PLAYER}", player.getName())
            .send();
    }

    private void sendRequesterNotification(Player player, Player requester) {
        this.noticeService
            .create()
            .player(requester.getUniqueId())
            .notice(translation -> translation.tpa().tpaDenyReceivedMessage())
            .placeholder("{PLAYER}", player.getName())
            .send();
    }

}
