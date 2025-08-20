package com.eternalcode.core.feature.playtime;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.util.DurationUtil;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.time.Duration;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

@Command(name = "playtime")
@Permission("eternalcore.playtime")
public class PlaytimeCommand {

    private final NoticeService noticeService;

    @Inject
    public PlaytimeCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Shows your playtime")
    void self(@Context Player player) {
        this.noticeService.create()
            .notice(translation -> translation.playtime().self())
            .placeholder("{PLAYTIME}", this.formatPlaytime(player))
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.playtime.other")
    @DescriptionDocs(description = "Shows playtime of a player", arguments = "<player>")
    void other(@Context Player player, @Arg Player target) {
        this.noticeService.create()
            .notice(translation -> translation.playtime().other())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{PLAYTIME}", this.formatPlaytime(target))
            .player(player.getUniqueId())
            .send();
    }

    private String formatPlaytime(Player player) {
        // WARNING: The enum name is misleading, it actually records ticks played
        int ticks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        Duration playtime = Duration.ofSeconds(ticks / 20L);
        return DurationUtil.format(playtime, true);
    }
}
