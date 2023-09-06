package com.eternalcode.core.feature.essentials;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "fly")
public class FlyCommand {

    private final NoticeService noticeService;

    @Inject
    public FlyCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.fly")
    @DescriptionDocs(description = "Toggle fly mode")
    void execute(Player player) {
        player.setAllowFlight(!player.getAllowFlight());

        this.noticeService.create()
            .notice(translation -> translation.player().flyMessage())
            .placeholder("{STATE}", translation -> player.getAllowFlight() ? translation.format().enable() : translation.format().disable())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(required = 1)
    @Permission("eternalcore.fly.other")
    @DescriptionDocs(description = "Toggle fly mode for specified player", arguments = "<player>")
    void execute(Viewer viewer, @Arg Player target) {
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
