package com.eternalcode.core.feature.essentials;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "feed")
public class FeedCommand {

    private final NoticeService noticeService;

    public FeedCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.feed")
    @DescriptionDocs(description = "Feed yourself")
    void execute(Player player) {
        this.feed(player);

        this.noticeService.create()
            .notice(translation -> translation.player().feedMessage())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.feed.other")
    @DescriptionDocs(description = "Feed other player", arguments = "<player>")
    void execute(Viewer viewer, @Arg Player target) {
        this.feed(target);

        this.noticeService.create()
            .notice(translation -> translation.player().feedMessage())
            .player(target.getUniqueId())
            .send();

        this.noticeService.create()
            .notice(translation -> translation.player().feedMessageBy())
            .placeholder("{PLAYER}", target.getName())
            .viewer(viewer)
            .send();
    }


    void feed(Player player) {
        player.setFoodLevel(20);
    }
}

