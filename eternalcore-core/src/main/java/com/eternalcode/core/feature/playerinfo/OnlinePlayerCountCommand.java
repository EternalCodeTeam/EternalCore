package com.eternalcode.core.feature.playerinfo;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Server;

@Command(name = "online")
@Permission("eternalcore.online")
class OnlinePlayerCountCommand {

    private final NoticeService noticeService;
    private final VanishService vanishService;
    private final Server server;

    @Inject
    OnlinePlayerCountCommand(NoticeService noticeService, VanishService vanishService, Server server) {
        this.noticeService = noticeService;
        this.vanishService = vanishService;
        this.server = server;
    }

    @Execute
    @DescriptionDocs(description = "Shows online players count")
    void execute(@Sender Viewer viewer) {
        long visiblePlayerCount = this.server.getOnlinePlayers().stream()
            .filter(player -> !this.vanishService.isVanished(player))
            .count();

        this.noticeService
            .create()
            .notice(translation -> translation.player().onlinePlayersCountMessage())
            .viewer(viewer)
            .placeholder("{ONLINE}", String.valueOf(visiblePlayerCount))
            .send();
    }
}
