package com.eternalcode.core.feature.teleport.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import dev.rollczi.litecommands.annotations.command.Command;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import panda.std.Option;

@Command(name = "back")
class TeleportBackCommand {

    private final TeleportService teleportService;
    private final NoticeService noticeService;

    @Inject
    TeleportBackCommand(TeleportService teleportService, NoticeService noticeService) {
        this.teleportService = teleportService;
        this.noticeService = noticeService;
    }

    @Execute
    @Permission("eternalcore.back")
    @DescriptionDocs(description = "Teleport to last location")
    void execute(@Context Player player) {
        Option<Location> location = this.teleportService.getLastLocation(player.getUniqueId());

        if (location.isEmpty()) {
            this.noticeService.player(player.getUniqueId(), translation -> translation.teleport().lastLocationNoExist());

            return;
        }

        this.teleportService.teleport(player, location.get());
        this.noticeService.player(player.getUniqueId(), translation -> translation.teleport().teleportedToLastLocation());
    }

    @Execute
    @Permission("eternalcore.back.other")
    @DescriptionDocs(description = "Teleport specified player to last location", arguments = "<player>")
    void execute(@Context Viewer viewer, @Arg Player player) {
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
