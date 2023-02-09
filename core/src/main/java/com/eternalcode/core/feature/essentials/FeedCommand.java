package com.eternalcode.core.feature.essentials;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "feed")
@Permission("eternalcore.feed")
public class FeedCommand {

    private final NoticeService noticeService;

    public FeedCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void execute(Player player) {
        player.setFoodLevel(20);

        this.noticeService.create()
            .notice(translation -> translation.player().feedMessage())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    void execute(Viewer viewer, @Arg Player target) {
        target.setFoodLevel(20);

        this.noticeService.create()
            .notice(translation -> translation.player().feedMessageBy())
            .placeholder("{PLAYER}", target.getName())
            .viewer(viewer)
            .send();
    }
}

