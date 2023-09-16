package com.eternalcode.core.feature.teleport.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.feature.teleport.TeleportService;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "tphere", aliases = { "s" })
@Permission("eternalcore.tphere")
class TeleportHereCommand {

    private final NoticeService noticeService;
    private final TeleportService teleportService;

    @Inject
    TeleportHereCommand(NoticeService noticeService, TeleportService teleportService) {
        this.noticeService = noticeService;
        this.teleportService = teleportService;
    }

    @Execute(required = 1)
    @DescriptionDocs(description = "Teleport player to you", arguments = "<player>")
    void tpHere(Player sender, @Arg Player target) {
        this.teleportService.teleport(target, sender.getLocation());
        this.noticeService.create()
            .notice(translation -> translation.teleport().teleportedPlayerToPlayer())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{ARG-PLAYER}", sender.getName())
            .player(sender.getUniqueId())
            .send();
    }

}
