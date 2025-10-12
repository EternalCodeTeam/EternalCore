package com.eternalcode.core.feature.teleport.tpa;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
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

@Command(name = "tpadeny", aliases = "tpdeny")
@Permission("eternalcore.tpadeny")
class TpaDenyCommand {

    private final TeleportRequestService requestService;
    private final NoticeService noticeService;
    private final Server server;

    @Inject
    TpaDenyCommand(TeleportRequestService requestService, NoticeService noticeService, Server server) {
        this.requestService = requestService;
        this.noticeService = noticeService;
        this.server = server;
    }

    @Execute
    @DescriptionDocs(description = "Deny teleport request", arguments = "<player>")
    void executeTarget(@Sender Player player, @Arg(RequesterArgument.KEY) Player target) {
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

    @Execute(name = "-all", aliases = "*")
    @DescriptionDocs(description = "Deny all teleport requests")
    void executeAll(@Sender Player player) {
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
