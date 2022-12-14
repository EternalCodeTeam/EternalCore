package com.eternalcode.core.chat.feature.adminchat;

import com.eternalcode.core.chat.notification.Notice;
import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Route(name = "adminchat", aliases = "ac")
@Permission("eternalcore.adminchat")
public class AdminChatCommand {

    private final NoticeService noticeService;
    private final Server server;

    public AdminChatCommand(NoticeService noticeService, Server server) {
        this.noticeService = noticeService;
        this.server = server;
    }

    @Execute(min = 1)
    public void execute(CommandSender sender, @Joiner String text) {
        Notice notice = this.noticeService.create()
            .console()
            .notice(messages -> messages.adminChat().format())
            .placeholder("{NICK}", sender.getName())
            .placeholder("{TEXT}", text);

        for (Player player : this.server.getOnlinePlayers()) {
            if (!player.hasPermission("eternalcore.adminchat.spy")) {
                continue;
            }

            notice = notice.player(player.getUniqueId());
        }

        notice.send();
    }
}
