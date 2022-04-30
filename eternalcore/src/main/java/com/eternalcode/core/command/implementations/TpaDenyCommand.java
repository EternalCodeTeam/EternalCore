package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.teleport.TeleportRequestManager;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;
import panda.std.Option;

import java.util.UUID;

@Section(route = "tpadeny")
@Permission("eternalcore.command.tpadeny")
public class TpaDenyCommand {

    private final TeleportRequestManager teleportRequestManager;
    private final NoticeService noticeService;

    public TpaDenyCommand(TeleportRequestManager teleportRequestManager, NoticeService noticeService) {
        this.teleportRequestManager = teleportRequestManager;
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(Player player) {

        Option<UUID> requestOption = teleportRequestManager.findTeleportRequest(player.getUniqueId());

        requestOption.peek(uuid -> {
            this.noticeService.notice()
                .message(messages -> messages.teleport().teleportDenyMessage())
                .placeholder("{PLAYER}", player.getName())
                .player(uuid)
                .send();
            this.teleportRequestManager.removeTeleportRequest(player.getUniqueId());
        }).onEmpty(() -> {
            this.noticeService.notice()
                .message(messages -> messages.teleport().noTeleportRequestsMessage())
                .player(player.getUniqueId())
                .send();
        });

    }
}
