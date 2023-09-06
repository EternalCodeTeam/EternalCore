package com.eternalcode.core.teleport.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Route(name = "tppos")
@Permission("eternalcore.tppos")
public class TeleportToPositionCommand {

    private final NoticeService noticeService;
    private final TeleportService teleportService;

    @Inject
    public TeleportToPositionCommand(NoticeService noticeService, TeleportService teleportService) {
        this.noticeService = noticeService;
        this.teleportService = teleportService;
    }

    @Execute(min = 3)
    @DescriptionDocs(description = "Teleport to specified coordinates", arguments = "<x> <y> <z>")
    void execute(Player player, @Arg int x, @Arg int y, @Arg int z) {
        this.teleport(player, x, y, z);
    }

    @Execute(min = 4)
    @DescriptionDocs(description = "Teleport specified player to specified coordinates", arguments = "<player> <x> <y> <z>")
    void execute(Viewer viewer, @Arg int x, @Arg int y, @Arg int z, @Arg Player target) {
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
