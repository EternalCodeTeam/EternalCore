package com.eternalcode.core.feature.teleport.tpa;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.List;
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
    @DescriptionDocs(description = "Accept teleport request", arguments = "<player>")
    void executeTarget(@Sender Player player, @Arg(RequesterArgument.KEY) Player target) {
        this.teleportTaskService.createTeleport(
            target.getUniqueId(),
            PositionAdapter.convert(target.getLocation()),
            PositionAdapter.convert(player.getLocation()),
            this.settings.tpaTimer()
        );

        this.requestService.removeRequest(target.getUniqueId());

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

    @Execute(name = "-all", aliases = "*")
    @DescriptionDocs(description = "Accept all teleport requests")
    void executeAll(@Sender Player player) {
        List<UUID> requests = this.requestService.findRequests(player.getUniqueId());

        if (requests.isEmpty()) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.tpa().tpaAcceptNoRequestMessage());

            return;
        }

        for (UUID uniqueId : requests) {
            Player requester = this.server.getPlayer(uniqueId);

            this.requestService.removeRequest(uniqueId);

            if (requester != null) {

                this.teleportTaskService.createTeleport(
                    requester.getUniqueId(),
                    PositionAdapter.convert(requester.getLocation()),
                    PositionAdapter.convert(player.getLocation()),
                    this.settings.tpaTimer()
                );

                this.noticeService
                    .create()
                    .player(uniqueId)
                    .notice(translation -> translation.tpa().tpaAcceptReceivedMessage())
                    .placeholder("{PLAYER}", player.getName())
                    .send();
            }
        }

        this.noticeService.player(player.getUniqueId(), translation -> translation.tpa().tpaAcceptAllAccepted());
    }
}
