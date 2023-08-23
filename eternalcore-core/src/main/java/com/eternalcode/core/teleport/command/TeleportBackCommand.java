package com.eternalcode.core.teleport.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notice.NoticeService;
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
public class TeleportBackCommand {

    private final TeleportService teleportService;
    private final NoticeService noticeService;

    public TeleportBackCommand(TeleportService teleportService, NoticeService noticeService) {
        this.teleportService = teleportService;
        this.noticeService = noticeService;
    }

    @Execute(required = 0)
    @Permission("eternalcore.back")
    @DescriptionDocs(description = "Teleport to last location")
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
    @Permission("eternalcore.back.other")
    @DescriptionDocs(description = "Teleport specified player to last location", arguments = "<player>")
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
