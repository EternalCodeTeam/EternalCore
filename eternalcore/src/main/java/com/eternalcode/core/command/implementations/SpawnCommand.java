package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.configuration.implementations.LocationsConfiguration;
import com.eternalcode.core.teleport.TeleportManager;
import com.eternalcode.core.command.argument.PlayerArg;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "spawn")
@Permission("eternalcore.command.spawn")
public class SpawnCommand {

    private final LocationsConfiguration locations;
    private final TeleportManager teleportManager;
    private final NoticeService noticeService;

    public SpawnCommand(LocationsConfiguration locations, NoticeService noticeService, TeleportManager teleportManager) {
        this.teleportManager = teleportManager;
        this.locations = locations;
        this.noticeService = noticeService;
    }

    @Execute
    public void execute(Player sender, @Arg(0) @Handler(PlayerArg.class) Option<Player> playerOption){
        Location location = this.locations.spawn;

        if (location == null || location.getWorld() == null) {
            this.noticeService
                .notice()
                .message(messages -> messages.other().spawnNoSet())
                .player(sender.getUniqueId())
                .send();

            return;
        }

        if (playerOption.isEmpty()) {
            if (sender.hasPermission("eternalcore.teleport.bypass")) {
                PaperLib.teleportAsync(sender, location);

                this.noticeService
                    .notice()
                    .message(messages -> messages.teleport().teleported())
                    .player(sender.getUniqueId())
                    .send();

                return;
            }

            if (this.teleportManager.inTeleport(sender.getUniqueId())) {
                this.noticeService
                    .notice()
                    .message(messages -> messages.teleport().haveTeleport())
                    .player(sender.getUniqueId())
                    .send();

                return;
            }

            this.teleportManager.createTeleport(sender.getUniqueId(), location, 10);

            this.noticeService
                .notice()
                .message(messages -> messages.teleport().teleporting())
                .player(sender.getUniqueId())
                .send();

            return;
        }

        Player player = playerOption.get();
        PaperLib.teleportAsync(player, location);

        this.noticeService
            .notice()
            .message(messages -> messages.other().spawnTeleportedBy())
            .placeholder("{PLAYER}", sender.getName())
            .player(player.getUniqueId())
            .send();

        this.noticeService
            .notice()
            .message(messages -> messages.other().spawnTeleportedOther())
            .placeholder("{PLAYER}", player.getName())
            .player(sender.getUniqueId())
            .send();
    }
}
