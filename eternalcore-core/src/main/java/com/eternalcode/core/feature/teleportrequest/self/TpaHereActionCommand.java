package com.eternalcode.core.feature.teleportrequest.self;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.core.feature.teleport.TeleportTaskService;
import com.eternalcode.core.feature.teleportrequest.TeleportRequestSettings;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.RootCommand;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

import static com.eternalcode.core.feature.teleportrequest.TeleportRequestPermissionConstant.TELEPORT_REQUEST_ACCEPT_PERMISSION;
import static com.eternalcode.core.feature.teleportrequest.TeleportRequestPermissionConstant.TELEPORT_REQUEST_HERE_DENY_PERMISSION;

@RootCommand
public class TpaHereActionCommand {

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
    @Permission(TELEPORT_REQUEST_ACCEPT_PERMISSION)
    void accept(@Context Player player, @Arg(SelfRequesterArgument.KEY) Player target) {
        this.teleportTaskService.createTeleport(
            player.getUniqueId(),
            PositionAdapter.convert(player.getLocation()),
            PositionAdapter.convert(target.getLocation()),
            this.settings.teleportTime()
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

    @Execute(name = "tpaheredeny")
    @Permission(TELEPORT_REQUEST_HERE_DENY_PERMISSION)
    @DescriptionDocs(description = "Deny a teleport here request")
    void executeTarget(@Context Player player, @Arg(SelfRequesterArgument.KEY) Player target) {
        this.requestService.removeRequest(target.getUniqueId());

        this.noticeService
            .create()
            .player(player.getUniqueId())
            .notice(translation -> translation.tpa().tpaDenyDoneMessage())
            .placeholder("{PLAYER}", target.getName())
            .send();

        this.noticeService
            .create()
            .player(target.getUniqueId())
            .notice(translation -> translation.tpa().tpaDenyReceivedMessage())
            .placeholder("{PLAYER}", player.getName())
            .send();
    }

    @Execute(name = "tpaheredeny -all")
    @Permission(TELEPORT_REQUEST_HERE_DENY_PERMISSION)
    @DescriptionDocs(description = "Deny all teleport here requests")
    void executeAll(@Context Player player) {
        List<UUID> requests = this.requestService.findRequests(player.getUniqueId());

        if (requests.isEmpty()) {

            this.noticeService.player(player.getUniqueId(), translation -> translation.tpa().tpaDenyNoRequestMessage());

            return;
        }

        for (UUID uniqueId : requests) {
            Player requester = this.server.getPlayer(uniqueId);

            this.requestService.removeRequest(uniqueId);

            if (requester != null) {

                this.noticeService
                    .create()
                    .player(uniqueId)
                    .notice(translation -> translation.tpa().tpaDenyReceivedMessage())
                    .placeholder("{PLAYER}", player.getName())
                    .send();
            }
        }

        this.noticeService.player(player.getUniqueId(), translation -> translation.tpa().tpaDenyAllDenied());
    }

}
