package com.eternalcode.core.feature.godmode;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "god", aliases = "godmode")
class GodCommand {

    private final NoticeService noticeService;

    @Inject
    GodCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.god")
    @DescriptionDocs(description = "Toggle god mode")
    void execute(@Context Player sender) {
        sender.setInvulnerable(!sender.isInvulnerable());

        this.noticeService.create()
            .player(sender.getUniqueId())
            .notice(translation -> sender.isInvulnerable()
                ? translation.player().godEnable()
                : translation.player().godDisable())
            .placeholder("{STATE}", translation -> sender.isInvulnerable()
                ? translation.format().enable()
                : translation.format().disable())
            .send();
    }

    @Execute
    @Permission("eternalcore.god.other")
    @DescriptionDocs(description = "Toggle god mode for specified player", arguments = "<player>")
    void execute(@Context Viewer viewer, @Arg Player target) {
        target.setInvulnerable(!target.isInvulnerable());

        this.noticeService.create()
            .player(target.getUniqueId())
            .notice(translation -> target.isInvulnerable()
                ? translation.player().godEnable()
                : translation.player().godDisable())
            .placeholder("{STATE}", translation -> target.isInvulnerable()
                ? translation.format().enable()
                : translation.format().disable())
            .send();


        this.noticeService.create()
            .notice(translation -> target.isInvulnerable()
                ? translation.player().godSetEnable()
                : translation.player().godSetDisable())
            .placeholder("{PLAYER}", target.getDisplayName())
            .placeholder("{STATE}", translation -> target.isInvulnerable()
                ? translation.format().enable()
                : translation.format().disable())
            .viewer(viewer)
            .send();
    }
}
