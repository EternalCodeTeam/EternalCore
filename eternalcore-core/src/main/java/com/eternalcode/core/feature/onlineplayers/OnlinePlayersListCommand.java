package com.eternalcode.core.feature.onlineplayers;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.feature.vanish.VanishService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import java.util.Collection;
import java.util.stream.Collectors;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

@Command(name = "list")
@Permission("eternalcore.list")
class OnlinePlayersListCommand {

    private final NoticeService noticeService;
    private final PluginConfiguration config;
    private final VanishService vanishService;
    private final Server server;

    @Inject
    OnlinePlayersListCommand(PluginConfiguration config, NoticeService noticeService,
        VanishService vanishService, Server server) {
        this.config = config;
        this.noticeService = noticeService;
        this.vanishService = vanishService;
        this.server = server;
    }

    @Execute
    @DescriptionDocs(description = "Shows online players list")
    void execute(@Sender Viewer viewer) {
        Collection<? extends Player> online = this.server.getOnlinePlayers()
            .stream()
            .filter(player -> !this.vanishService.isVanished(player))
            .toList();

        String onlineCount = String.valueOf(online.size());
        String players = online.stream()
            .map(HumanEntity::getName)
            .collect(Collectors.joining(this.config.format.separator));

        this.noticeService.create()
            .notice(translation -> translation.online().onlinePlayersList())
            .placeholder("{ONLINE}", onlineCount)
            .placeholder("{PLAYERS}", players)
            .viewer(viewer)
            .send();
    }
}
