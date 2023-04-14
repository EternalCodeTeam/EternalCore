package com.eternalcode.core.feature.adminchat;

import com.eternalcode.annotations.scan.command.Description;
import com.eternalcode.annotations.scan.feature.FeatureDocs;
import com.eternalcode.core.notification.Notice;
import com.eternalcode.core.notification.NoticeService;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Route(name = "adminchat", aliases = "ac")
@Permission("eternalcore.adminchat")
@FeatureDocs(
    name = "AdminChat",
    description = "Allows you to send a message to all staff members",
    permission = { "eternalcore.adminchat", "eternalcore.adminchat.spy" }
)
public class AdminChatCommand {

    private final NoticeService noticeService;
    private final Server server;

    public AdminChatCommand(NoticeService noticeService, Server server) {
        this.noticeService = noticeService;
        this.server = server;
    }

    @Execute(min = 1)
    @Description("Allows you to send a message to all staff members")
    public void execute(CommandSender sender, @Joiner String message) {
        Notice notice = this.noticeService.create()
            .console()
            .notice(translation -> translation.adminChat().format())
            .placeholder("{PLAYER}", sender.getName())
            .placeholder("{TEXT}", message);

        for (Player player : this.server.getOnlinePlayers()) {
            if (!player.hasPermission("eternalcore.adminchat.spy")) {
                continue;
            }

            notice = notice.player(player.getUniqueId());
        }

        notice.send();
    }
}
