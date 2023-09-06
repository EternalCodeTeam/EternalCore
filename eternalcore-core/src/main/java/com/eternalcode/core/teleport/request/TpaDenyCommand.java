package com.eternalcode.core.teleport.request;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.command.argument.RequesterArgument;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.amount.Required;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@Route(name = "tpadeny", aliases = "tpdeny")
@Permission("eternalcore.tpadeny")
public class TpaDenyCommand {

    private final TeleportRequestService requestService;
    private final NoticeService noticeService;
    private final Server server;

    @Inject
    public TpaDenyCommand(TeleportRequestService requestService, NoticeService noticeService, Server server) {
        this.requestService = requestService;
        this.noticeService = noticeService;
        this.server = server;
    }

    @Execute
    @Required(1)
    @DescriptionDocs(description = "Deny teleport request", arguments = "<player>")
    public void executeTarget(Player player, @Arg @By(RequesterArgument.KEY) Player target) {
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

    @Execute(route = "-all", aliases = "*")
    @DescriptionDocs(description = "Deny all teleport requests")
    public void executeAll(Player player) {
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
