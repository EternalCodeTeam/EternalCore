package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.RequesterArgument;
import com.eternalcode.core.teleport.TeleportRequestService;

import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Required;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@Section(route = "tpadeny", aliases = "tpdeny")
@Permission("eternalcore.command.tpadeny")
public class TpaDenyCommand {

    private final TeleportRequestService requestService;
    private final NoticeService noticeService;
    private final Server server;

    public TpaDenyCommand(TeleportRequestService requestService, NoticeService noticeService, Server server) {
        this.requestService = requestService;
        this.noticeService = noticeService;
        this.server = server;
    }

    @Execute
    @Required(1)
    public void excecuteTarget(Player player, @Arg(0) @Handler(RequesterArgument.class) Player target) {
        this.requestService.removeRequest(target.getUniqueId());

        this.noticeService
            .notice()
            .player(player.getUniqueId())
            .message(messages -> messages.tpa().tpaDenyDoneMessage())
            .placeholder("{PLAYER}", target.getName())
            .send();

        this.noticeService
            .notice()
            .player(target.getUniqueId())
            .message(messages -> messages.tpa().tpaDenyRecivedMessage())
            .placeholder("{PLAYER}", player.getName())
            .send();
    }

    @Execute(route = "-all", aliases = "*")
    public void executeAll(Player player) {
        List<UUID> requests = this.requestService.findRequests(player.getUniqueId());

        if (requests.isEmpty()) {

            this.noticeService.player(player.getUniqueId(), messages -> messages.tpa().tpaDenyNoRequestMessage());

            return;
        }

        for (UUID uniqueId : requests) {
            Player requester = this.server.getPlayer(uniqueId);

            this.requestService.removeRequest(uniqueId);

            if (requester != null) {

                this.noticeService
                    .notice()
                    .player(uniqueId)
                    .message(messages -> messages.tpa().tpaDenyRecivedMessage())
                    .placeholder("{PLAYER}", player.getName())
                    .send();
            }
        }

        this.noticeService.player(player.getUniqueId(), messages -> messages.tpa().tpaDenyAllDenied());
    }
}
