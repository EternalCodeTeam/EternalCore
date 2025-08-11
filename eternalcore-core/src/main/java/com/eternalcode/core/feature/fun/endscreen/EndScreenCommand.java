package com.eternalcode.core.feature.fun.endscreen;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.paper.PaperOverlay;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "endscreen", aliases = {"end-screen", "win-screen"})
@Permission("eternalcore.troll.endscreen")
public class EndScreenCommand {

    private final NoticeService noticeService;

    @Inject
    public EndScreenCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Show a end screen to yourself")
    void self(@Context Player sender) {
        PaperOverlay.END_SCREEN.show(sender);

        this.noticeService.create()
            .notice(translation -> translation.endScreen().shownToSelf())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute
    @DescriptionDocs(description = "Show a end screen to a player", arguments = "<player>")
    void other(@Context Player sender, @Arg Player target) {
        PaperOverlay.END_SCREEN.show(target);

        this.noticeService.create()
            .notice(translation -> translation.endScreen().shownToOtherPlayer())
            .player(sender.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

}
