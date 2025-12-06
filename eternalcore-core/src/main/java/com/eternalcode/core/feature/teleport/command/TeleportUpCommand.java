package com.eternalcode.core.feature.teleport.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Command(name = "tpup", aliases = { "up", "teleportup" })
@Permission("eternalcore.tpup")
class TeleportUpCommand {

    private final TeleportService teleportService;
    private final NoticeService noticeService;

    @Inject
    TeleportUpCommand(TeleportService teleportService, NoticeService noticeService) {
        this.teleportService = teleportService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Teleport you to the highest block")
    void tpUp(@Sender Player player) {
        this.teleportPlayerToHighestBlock(player);
    }

    private void teleportPlayerToHighestBlock(Player player) {
        Location playerLocation = player.getLocation();
        World world = player.getWorld();
        int highestBlockYAt = world.getHighestBlockYAt(playerLocation);

        Location newLocation = playerLocation.getBlock().getLocation();

        newLocation.setY(highestBlockYAt);
        newLocation.add(.5, 1.0, .5);
        newLocation.setPitch(playerLocation.getPitch());
        newLocation.setYaw(playerLocation.getYaw());

        this.teleportService.teleport(player, newLocation);

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.teleport().teleportedToHighestBlock())
            .placeholder("{Y}", String.valueOf(Math.round(newLocation.getY())))
            .send();
    }

}
