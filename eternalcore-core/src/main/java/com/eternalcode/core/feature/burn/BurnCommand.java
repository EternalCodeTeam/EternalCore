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

import java.util.Optional;

@Command(name = "burn", aliases = {"ignite"})
@Permission("eternalcore.burn")
public class BurnCommand {

    private static final int DEFAULT_BURN_TICK_DURATION = 5 * 20;
    private final NoticeService noticeService;

    @Inject
    public BurnCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Burns yourself for a specified amount of ticks.", arguments = "[ticks]")
    void self(@Context Player sender, @Arg Optional<Integer> ticks) {
        this.burn(sender, sender, ticks);
    }

    @Execute
    @Permission("eternalcore.burn.other")
    @DescriptionDocs(description = "Burns target for a specified amount of ticks.", arguments = "<target> [ticks]")
    void other(@Context Player sender, @Arg Player target, @Arg Optional<Integer> ticks) {
        this.burn(sender, target, ticks);
    }

    private void burn(Player sender, Player target, Optional<Integer> ticks) {
        int actualTicks = ticks.orElse(DEFAULT_BURN_TICK_DURATION);

        if (actualTicks <= 0) {
            this.noticeService.create()
                .player(sender.getUniqueId())
                .notice(translation -> translation.argument().numberBiggerThanZero())
                .send();
            return;
        }

        target.setFireTicks(actualTicks);

        String ticksString = String.valueOf(actualTicks);

        if (sender.equals(target)) {
            this.noticeService.create()
                .player(sender.getUniqueId())
                .placeholder("{TICKS}", ticksString)
                .notice(translation -> translation.burn().burnedSelf())
                .send();
            return;
        }

        this.noticeService.create()
            .player(sender.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{TICKS}", ticksString)
            .notice(translation -> translation.burn().burnedOther())
            .send();

        this.noticeService.create()
            .player(target.getUniqueId())
            .placeholder("{TICKS}", ticksString)
            .notice(translation -> translation.burn().burnedSelf())
            .send();
    }
}
