package com.eternalcode.core.feature.freeze;

import com.eternalcode.commons.time.DurationTickUtil;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

import java.time.Duration;

@Command(name = "freeze")
@Permission("eternalcore.freeze")
public class FreezeCommand {

    private final NoticeService noticeService;

    @Inject
    public FreezeCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }
    @Execute
    public void freezeSelf(@Context Player player, @Arg Duration duration) {
        this.freezePlayer(player, duration);
        this.noticeService.create()
            .notice(translation -> translation.freeze().frozenSelf())
            .placeholder("{DURATION}", DurationUtil.format(duration, true))
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.freeze.other")
    public void freezeOther(@Context Player player, @Arg Player target, @Arg Duration duration) {
        this.freezePlayer(target, duration);

        this.noticeService.create()
            .notice(translation -> translation.freeze().frozenOther())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{DURATION}", DurationUtil.format(duration, true))
            .player(player.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.freeze().frozenByOther())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{DURATION}", DurationUtil.format(duration, true))
            .player(target.getUniqueId())
            .send();
    }

    private void freezePlayer(Player player, Duration duration) {
        int ticks = DurationTickUtil.durationToTicks(duration);
        player.setFreezeTicks(ticks);
    }
}
