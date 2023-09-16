package com.eternalcode.core.feature.essentials.playerinfo;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "ping")
class PingCommand {

    private final NoticeService noticeService;

    @Inject
    PingCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.ping")
    @DescriptionDocs(description = "Shows your ping")
    void execute(Player sender) {
        this.noticeService.create()
            .notice(translation -> translation.player().pingMessage())
            .placeholder("{PING}", String.valueOf(sender.getPing()))
            .player(sender.getUniqueId())
            .send();
    }


    @Execute(required = 1)
    @Permission("eternalcore.ping.other")
    @DescriptionDocs(description = "Shows ping of other player", arguments = "<player>")
    void execute(Viewer viewer, @Arg Player target) {
        this.noticeService.create()
            .notice(translation -> translation.player().pingOtherMessage())
            .placeholder("{PING}", String.valueOf(target.getPing()))
            .placeholder("{PLAYER}", target.getName())
            .viewer(viewer)
            .send();
    }

}
