package com.eternalcode.core.feature.burn;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "burn", aliases = {"fire"})
@Permission("eternalcore.troll.burn")
public class BurnCommand {

    private final BurnSettings settings;
    private final NoticeService noticeService;

    @Inject
    public BurnCommand(BurnSettings settings, NoticeService noticeService) {
        this.settings = settings;
        this.noticeService = noticeService;
    }

    @Execute
    void self(@Context Player sender) {
        this.self(sender, this.settings.defaultBurnDuration());
    }

    @Execute
    @DescriptionDocs(description = "Burns yourself for a specified amount of ticks.", arguments = "[ticks]")
    void self(@Context Player sender, @Arg int ticks) {
        sender.setFireTicks(ticks);

        this.noticeService.create()
            .player(sender.getUniqueId())
            .placeholder("{TICKS}", String.valueOf(ticks))
            .notice(translation -> translation.burn().burnedSelf())
            .send();
    }

    @Execute
    void other(@Context Player sender, @Arg Player target) {
        this.other(sender, target, this.settings.defaultBurnDuration());
    }

    @Execute
    @DescriptionDocs(description = "Burns target for a specified amount of ticks.", arguments = "<target> [ticks]")
    void other(@Context Player sender, @Arg Player target, @Arg int ticks) {
        target.setFireTicks(ticks);

        this.noticeService.create()
            .player(sender.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{TICKS}", String.valueOf(ticks))
            .notice(translation -> translation.burn().burnedOther())
            .send();
    }

}
