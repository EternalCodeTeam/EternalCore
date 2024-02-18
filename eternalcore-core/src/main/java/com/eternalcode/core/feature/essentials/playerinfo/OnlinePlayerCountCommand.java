package com.eternalcode.core.feature.essentials.playerinfo;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Server;
import org.bukkit.entity.Player;

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
    void execute(@Context Viewer viewer) {
        long visiblePlayerCount = this.server.getOnlinePlayers().stream()
            .filter(player -> !this.vanishService.isVanished(player.getUniqueId()))
            .count();

        this.noticeService
            .create()
            .notice(translation -> translation.player().onlinePlayersCountMessage())
            .viewer(viewer)
            .placeholder("{ONLINE}", String.valueOf(visiblePlayerCount))
            .send();
    }
}
