package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.Notice;
import com.eternalcode.core.chat.notification.NoticeService;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "adminchat", aliases = "ac")
@Permission("eternalcore.command.adminchat")
public class AdminChatCommand {

    private final NoticeService noticeService;
    private final Server server;

    public AdminChatCommand(NoticeService noticeService, Server server) {
        this.noticeService = noticeService;
        this.server = server;
    }

    @Execute(min = 1)
    public void execute(CommandSender sender, @Joiner String text) {
        Notice notice = this.noticeService.notice()
            .console()
            .message(messages -> messages.adminChat().format())
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
