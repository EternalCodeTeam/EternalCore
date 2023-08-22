package com.eternalcode.core.feature.essentials;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "god", aliases = "godmode")
public class GodCommand {

    private final NoticeService noticeService;

    public GodCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.god")
    @DescriptionDocs(description = "Toggle god mode")
    void execute(Player sender) {
        sender.setInvulnerable(!sender.isInvulnerable());

        this.noticeService.create()
            .placeholder("{STATE}", translation -> sender.isInvulnerable() ? translation.format().enable() : translation.format().disable())
            .notice(translation -> translation.player().godMessage())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute(required = 1)
    @Permission("eternalcore.god.other")
    @DescriptionDocs(description = "Toggle god mode for specified player", arguments = "<player>")
    void execute(Viewer viewer, @Arg Player target) {
        target.setInvulnerable(!target.isInvulnerable());

        this.noticeService.create()
            .placeholder("{STATE}", translation -> target.isInvulnerable() ? translation.format().enable() : translation.format().disable())
            .notice(translation -> translation.player().godMessage())
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .placeholder("{STATE}", translation -> target.isInvulnerable() ? translation.format().enable() : translation.format().disable())
            .placeholder("{PLAYER}", target.getName())
            .notice(translation -> translation.player().godSetMessage())
            .viewer(viewer)
            .send();
    }

}
