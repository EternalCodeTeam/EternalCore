package com.eternalcode.core.feature.feed;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.entity.Player;

@Command(name = "feed")
class FeedCommand {

    private final NoticeService noticeService;

    @Inject
    FeedCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.feed")
    @DescriptionDocs(description = "Feed yourself")
    void execute(@Context Player player) {
        this.feed(player);

        this.noticeService.create()
            .notice(translation -> translation.player().feedMessage())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @Permission("eternalcore.feed.other")
    @DescriptionDocs(description = "Feed other player", arguments = "<player>")
    void execute(@Context Viewer viewer, @Arg Player target) {
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

