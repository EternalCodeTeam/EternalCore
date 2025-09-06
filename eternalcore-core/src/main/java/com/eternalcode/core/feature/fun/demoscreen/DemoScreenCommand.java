package com.eternalcode.core.feature.fun.demoscreen;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.compatibility.Compatibility;
import com.eternalcode.core.compatibility.Version;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "demoscreen", aliases = {"demo"})
@Permission("eternalcore.troll.demoscreen")
@Compatibility(from = @Version(minor = 18, patch = 2))
public class DemoScreenCommand {

    private final NoticeService noticeService;

    @Inject
    public DemoScreenCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = {"Show a demo screen to yourself"})
    void self(@Sender Player sender) {
        sender.showDemoScreen();

        this.noticeService.create()
            .notice(translation -> translation.demoScreen().shownToSelf())
            .player(sender.getUniqueId())
            .send();
    }

    @Execute
    @DescriptionDocs(description = {"Show a demo screen to a player"}, arguments = {"<player>"})
    void other(@Sender Player sender, @Arg Player target) {
        target.showDemoScreen();

        this.noticeService.create()
            .notice(translation -> translation.demoScreen().shownToOtherPlayer())
            .player(sender.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .send();
    }

}
