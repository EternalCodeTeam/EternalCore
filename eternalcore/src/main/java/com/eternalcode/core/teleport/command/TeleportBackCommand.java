package com.eternalcode.core.teleport.command;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import panda.std.Option;

@Route(name = "back")
public class TeleportBackCommand {

    private final TeleportService teleportService;
    private final NoticeService noticeService;

    public TeleportBackCommand(TeleportService teleportService, NoticeService noticeService) {
        this.teleportService = teleportService;
        this.noticeService = noticeService;
    }

    @Execute(required = 0)
    void execute(Player player) {
        Option<Location> location = this.teleportService.getLastLocation(player.getUniqueId());

        if (location.isEmpty()) {
            this.noticeService.create()
                .notice(messages -> messages.teleport().lastLocationNoExist())
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.teleportService.teleport(player, location.get());

        this.noticeService.create()
            .notice(messages -> messages.teleport().teleportedToLastLocation())
            .player(player.getUniqueId())
            .send();
    }

    @Execute(required = 1)
    void execute(Viewer viewer, @Arg Player player) {
        Option<Location> location = this.teleportService.getLastLocation(player.getUniqueId());

        if (location.isEmpty()) {
            this.noticeService.create()
                .notice(messages -> messages.teleport().lastLocationNoExist())
                .viewer(viewer)
                .send();

            return;
        }

        this.teleportService.teleport(player, location.get());

        this.noticeService.create()
            .notice(messages -> messages.teleport().teleportedToLastLocation())
            .player(player.getUniqueId())
            .send();

        this.noticeService.create()
            .viewer(viewer)
            .notice(messages -> messages.teleport().teleportedSpecifiedPlayerLastLocation())
            .placeholder("{PLAYER}", player.getName())
            .send();
    }

}
