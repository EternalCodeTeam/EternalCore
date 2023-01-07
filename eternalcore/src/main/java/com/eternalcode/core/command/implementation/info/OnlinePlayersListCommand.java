package com.eternalcode.core.command.implementation.info;

import com.eternalcode.core.configuration.implementation.PluginConfiguration;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import panda.utilities.text.Joiner;

import java.util.Collection;

@Route(name = "list")
@Permission("eternalcore.list")
public class OnlinePlayersListCommand {

    private final NoticeService noticeService;
    private final PluginConfiguration config;
    private final Server server;

    public OnlinePlayersListCommand(PluginConfiguration config, NoticeService noticeService, Server server) {
        this.config = config;
        this.noticeService = noticeService;
        this.server = server;
    }

    @Execute
    void execute(Viewer viewer) {
        Collection<? extends Player> online = this.server.getOnlinePlayers();

        String onlineCount = String.valueOf(online.size());
        String players = Joiner.on(this.config.format.separator)
            .join(online, HumanEntity::getName)
            .toString();

        this.noticeService.create()
            .notice(translation -> translation.player().listMessage())
            .placeholder("{ONLINE}", onlineCount)
            .placeholder("{PLAYERS}", players)
            .viewer(viewer)
            .send();
    }
}
