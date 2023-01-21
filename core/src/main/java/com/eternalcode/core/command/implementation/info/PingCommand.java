package com.eternalcode.core.command.implementation.info;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Route(name = "ping")
@Permission("eternalcore.ping")
public class PingCommand {

    private final NoticeService noticeService;

    public PingCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute
    void execute(CommandSender sender, Viewer audience, @Arg @By("or_sender") Player player) {
        if (sender.equals(player)) {
            this.noticeService.create()
                .notice(translation -> translation.player().pingMessage())
                .placeholder("{PING}", String.valueOf(player.getPing()))
                .viewer(audience)
                .send();

            return;
        }

        this.noticeService.create()
            .notice(translation -> translation.player().pingOtherMessage())
            .placeholder("{PING}", String.valueOf(player.getPing()))
            .placeholder("{PLAYER}", player.getName())
            .viewer(audience)
            .send();
    }
}
