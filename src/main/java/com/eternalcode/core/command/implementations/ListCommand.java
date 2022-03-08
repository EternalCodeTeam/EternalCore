package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerArgOrSender;
import com.eternalcode.core.configuration.implementations.PluginConfiguration;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import panda.utilities.text.Joiner;

import java.util.Collection;
import java.util.stream.Collectors;

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
    public void execute(Audience audience) {
        Collection<? extends Player> online = this.server.getOnlinePlayers();
        
        String onlineCount = String.valueOf(online.size());
        String players = Joiner.on(this.config.format.separator)
            .join(online, HumanEntity::getName)
            .toString();

        this.noticeService.notice()
            .message(messages -> messages.other().listMessage())
            .placeholder("{ONLINE}", onlineCount)
            .placeholder("{PLAYERS}", players)
            .audience(audience)
            .send();
    }
}
