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

@Command(name = "tppos")
@Permission("eternalcore.tppos")
class TeleportToPositionCommand {

    private final NoticeService noticeService;
    private final TeleportService teleportService;

    @Inject
    TeleportToPositionCommand(NoticeService noticeService, TeleportService teleportService) {
        this.noticeService = noticeService;
        this.teleportService = teleportService;
    }

    @Execute
    @DescriptionDocs(description = "Teleport to specified coordinates", arguments = "<x> <y> <z>")
    void execute(@Context Player player, @Arg int x, @Arg int y, @Arg int z) {
        this.teleport(player, x, y, z);
    }

    @Execute
    @DescriptionDocs(description = "Teleport specified player to specified coordinates", arguments = "<x> <y> <z> <player>")
    void execute(@Context Viewer viewer, @Arg int x, @Arg int y, @Arg int z, @Arg Player target) {
        this.teleport(target, x, y, z);

        this.noticeService.create()
            .notice(translation -> translation.teleport().teleportedSpecifiedPlayerToCoordinates())
            .placeholder("{PLAYER}", target.getName())
            .placeholder("{X}", String.valueOf(x))
            .placeholder("{Y}", String.valueOf(y))
            .placeholder("{Z}", String.valueOf(z))
            .viewer(viewer)
            .send();
    }

    private void teleport(Player player, int x, int y, int z) {
        Location location = new Location(player.getWorld(), x, y, z);

        this.teleportService.teleport(player, location);
        this.noticeService.create()
            .notice(translation -> translation.teleport().teleportedToCoordinates())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{X}", String.valueOf(x))
            .placeholder("{Y}", String.valueOf(y))
            .placeholder("{Z}", String.valueOf(z))
            .player(player.getUniqueId())
            .send();
    }
}
