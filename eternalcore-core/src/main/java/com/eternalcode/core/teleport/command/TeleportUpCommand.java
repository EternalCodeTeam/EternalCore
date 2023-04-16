package com.eternalcode.core.teleport.command;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notification.NoticeService;
import com.eternalcode.core.teleport.TeleportService;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Route(name = "tpup", aliases = { "up", "teleportup" })
@Permission("eternalcore.tpup")
public class TeleportUpCommand {

    private final TeleportService teleportService;
    private final NoticeService noticeService;

    public TeleportUpCommand(TeleportService teleportService, NoticeService noticeService) {
        this.teleportService = teleportService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Teleport you to the highest block")
    void tpUp(Player player) {
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
