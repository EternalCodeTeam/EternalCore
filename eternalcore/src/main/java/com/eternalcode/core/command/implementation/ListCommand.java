package com.eternalcode.core.command.implementation;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.permission.Permission;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import panda.utilities.text.Joiner;

import java.util.Collection;

@Section(route = "list")
@Permission("eternalcore.command.list")
public class ListCommand {

    private final NoticeService noticeService;
    private final PluginConfiguration config;
    private final Server server;

    public ListCommand(PluginConfiguration config, NoticeService noticeService, Server server) {
        this.config = config;
        this.noticeService = noticeService;
        this.server = server;
    }

    @Execute
    public void execute(Viewer audience) {
        Collection<? extends Player> online = this.server.getOnlinePlayers();

        String onlineCount = String.valueOf(online.size());
        String players = Joiner.on(this.config.format.separator)
            .join(online, HumanEntity::getName)
            .toString();

        this.noticeService.notice()
            .message(messages -> messages.other().listMessage())
            .placeholder("{ONLINE}", onlineCount)
            .placeholder("{PLAYERS}", players)
            .viewer(audience)
            .send();
    }
}
