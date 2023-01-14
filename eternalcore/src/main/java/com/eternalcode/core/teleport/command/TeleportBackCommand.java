package com.eternalcode.core.teleport.command;

import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import panda.std.Option;

@Route(name = "back")
@Permission("eternalcore.back")
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
            this.noticeService.player(player.getUniqueId(), translation -> translation.teleport().lastLocationNoExist());

            return;
        }

        this.teleportService.teleport(player, location.get());

        this.noticeService.player(player.getUniqueId(), translation -> translation.teleport().teleportedToLastLocation());
    }

    @Execute(required = 1)
    void execute(Viewer viewer, @Arg Player player) {
        Option<Location> location = this.teleportService.getLastLocation(player.getUniqueId());

        if (location.isEmpty()) {
            this.noticeService.viewer(viewer, translation -> translation.teleport().lastLocationNoExist());

            return;
        }

        this.teleportService.teleport(player, location.get());

        this.noticeService.player(player.getUniqueId(), translation -> translation.teleport().teleportedToLastLocation());

        this.noticeService.create()
            .viewer(viewer)
            .notice(translation -> translation.teleport().teleportedSpecifiedPlayerLastLocation())
            .placeholder("{PLAYER}", player.getName())
            .send();
    }

}
