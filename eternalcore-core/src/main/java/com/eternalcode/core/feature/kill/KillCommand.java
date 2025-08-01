package com.eternalcode.core.feature.kill;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.commons.bukkit.scheduler.MinecraftScheduler;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

@Command(name = "kill")
@Permission("eternalcore.kill")
class KillCommand {

    private final NoticeService noticeService;
    private final MinecraftScheduler scheduler;

    @Inject
    KillCommand(NoticeService noticeService, MinecraftScheduler scheduler) {
        this.noticeService = noticeService;
        this.scheduler = scheduler;
    }

    @Execute
    @DescriptionDocs(description = "Kill yourself")
    void execute(@Context Player player) {
        player.setHealth(0);

        this.noticeService.create()
            .notice(translation -> translation.player().killedMessage())
            .placeholder("{PLAYER}", player.getName())
            .player(player.getUniqueId())
            .send();
    }

    @Execute
    @DescriptionDocs(description = "Kill specified player", arguments = "<player>")
    void execute(@Context Viewer audience, @Arg Player player) {
        this.scheduler.run(player, () -> player.setHealth(0));

        this.noticeService.create()
            .notice(translation -> translation.player().killedMessage())
            .placeholder("{PLAYER}", player.getName())
            .viewer(audience)
            .send();
    }

}
