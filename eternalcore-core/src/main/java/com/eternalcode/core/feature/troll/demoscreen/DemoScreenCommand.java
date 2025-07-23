package com.eternalcode.core.feature.troll.demoscreen;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "demoscreen", aliases = {"demo"})
@Permission("eternalcore.troll.demoscreen")
public class DemoScreenCommand {

    private final NoticeService noticeService;

    @Inject
    public DemoScreenCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = {"Show a demo screen to yourself"})
    void self(@Context Player sender) {
        sender.showDemoScreen();

        this.noticeService.create()
            .notice(translation -> translation.troll().demoScreen().demoScreenShownToSelf())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute
    @DescriptionDocs(description = {"Show a demo screen to a player"}, arguments = {"<player>"})
    void execute(@Context Player sender, @Arg Player target) {
        target.showDemoScreen();

        this.noticeService.create()
            .notice(translation -> translation.troll().demoScreen().demoScreenShownToPlayer())
            .player(sender.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

}
