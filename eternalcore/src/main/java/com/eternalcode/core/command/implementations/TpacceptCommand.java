package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.teleport.TeleportManager;
import com.eternalcode.core.teleport.TeleportRequest;
import com.eternalcode.core.teleport.TeleportRequestManager;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.util.UUID;

@Section(route = "tpaccept")
@Permission("eternalcore.command.tpaccept")
public class TpacceptCommand {

    private final TeleportRequestManager teleportRequestManager;
    private final TeleportManager teleportManager;
    private final Server server;
    private final NoticeService noticeService;

    public TpacceptCommand(TeleportRequestManager teleportRequestManager, Server server, NoticeService noticeService, TeleportManager teleportManager) {
        this.teleportRequestManager = teleportRequestManager;
        this.server = server;
        this.noticeService = noticeService;
        this.teleportManager = teleportManager;
    }

    @Execute
    public void execute(Player player) {

        Option<UUID> requestOption = teleportRequestManager.findTeleportRequest(player.getUniqueId());

        requestOption.peek(uuid -> {
            this.noticeService.notice()
                .message(messages -> messages.teleport().teleportAcceptMessage())
                .placeholder("{PLAYER}", player.getName())
                .player(uuid)
                .send();
            Player target = server.getPlayer(uuid);
            this.teleportManager.createTeleport(target.getUniqueId(), target.getLocation(), player.getLocation(), 5);
            this.teleportRequestManager.removeTeleportRequest(player.getUniqueId());
        }).onEmpty(() -> {
           this.noticeService.notice()
               .message(messages -> messages.teleport().noTeleportRequestsMessage())
               .player(player.getUniqueId())
               .send();
        });

    }
}
