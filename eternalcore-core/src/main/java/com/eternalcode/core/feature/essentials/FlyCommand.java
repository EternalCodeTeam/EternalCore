package com.eternalcode.core.feature.essentials;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.entity.Player;

@Command(name = "fly")
class FlyCommand {

    private final NoticeService noticeService;

    @Inject
    FlyCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(required = 0)
    @Permission("eternalcore.fly")
    @DescriptionDocs(description = "Toggle fly mode")
    void execute(@Context Player player) {
        player.setAllowFlight(!player.getAllowFlight());

        this.noticeService.create()
            .notice(translation -> translation.player().flyMessage())
            .placeholder("{STATE}", translation -> player.getAllowFlight() ? translation.format().enable() : translation.format().disable())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.fly.other")
    @DescriptionDocs(description = "Toggle fly mode for specified player", arguments = "<player>")
    void execute(@Context Viewer viewer, @Arg Player target) {
        target.setAllowFlight(!target.getAllowFlight());

        this.noticeService.create()
            .notice(translation -> translation.player().flyMessage())
            .placeholder("{STATE}", translation -> target.getAllowFlight() ? translation.format().enable() : translation.format().disable())
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.player().flySetMessage())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{STATE}", translation -> target.getAllowFlight() ? translation.format().enable() : translation.format().disable())
            .viewer(viewer)
            .send();
    }
}
