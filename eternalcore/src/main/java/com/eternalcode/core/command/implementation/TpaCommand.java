package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerArg;
import com.eternalcode.core.teleport.TeleportRequestService;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Required;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.entity.Player;

@Section(route = "tpa")
@Permission("eternalcore.command.tpa")
public class TpaCommand {

    private final TeleportRequestService requestService;
    private final NoticeService noticeService;

    public TpaCommand(TeleportRequestService requestService, NoticeService noticeService) {
        this.requestService = requestService;
        this.noticeService = noticeService;
    }

    @Execute
    @Required(1)
    public void execute(Player player, @Arg(0) @Handler(PlayerArg.class) Player target) {
        if (player.equals(target)) {

            this.noticeService
                .notice()
                .player(player.getUniqueId())
                .message(messages -> messages.tpa().tpaSelfMessage())
                .send();

            return;
        }

        if (this.requestService.hasRequest(player.getUniqueId(), target.getUniqueId())) {

            this.noticeService
                .notice()
                .player(player.getUniqueId())
                .message(messages -> messages.tpa().tpaAlreadySentMessage())
                .send();

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
            .message(messages -> messages.tpa().tpaRecivedMessage())
            .placeholder("{PLAYER}", player.getName())
            .send();

        this.requestService.createRequest(player.getUniqueId(), target.getUniqueId());
    }
}

