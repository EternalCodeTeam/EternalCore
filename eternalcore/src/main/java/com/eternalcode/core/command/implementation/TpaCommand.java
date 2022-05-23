package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.teleport.TeleportRequestService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.entity.Player;

@Section(route = "tpa")
@Permission("eternalcore.tpa")
public class TpaCommand {

    private final TeleportRequestService requestService;
    private final NoticeService noticeService;

    public TpaCommand(TeleportRequestService requestService, NoticeService noticeService) {
        this.requestService = requestService;
        this.noticeService = noticeService;
    }

    @Execute(required = 1)
    public void execute(Player player, @Arg Player target) {
        if (player.equals(target)) {

            this.noticeService.player(player.getUniqueId(), messages -> messages.tpa().tpaSelfMessage());

            return;
        }

        if (this.requestService.hasRequest(player.getUniqueId(), target.getUniqueId())) {

            this.noticeService.player(player.getUniqueId(), messages -> messages.tpa().tpaAlreadySentMessage());

            return;
        }

        this.noticeService
            .notice()
            .player(player.getUniqueId())
            .message(messages -> messages.tpa().tpaSentMessage())
            .placeholder("{PLAYER}", target.getName())
            .send();

        this.noticeService
            .notice()
            .player(target.getUniqueId())
            .message(messages -> messages.tpa().tpaReceivedMessage())
            .placeholder("{PLAYER}", player.getName())
            .send();

        this.requestService.createRequest(player.getUniqueId(), target.getUniqueId());
    }
}

