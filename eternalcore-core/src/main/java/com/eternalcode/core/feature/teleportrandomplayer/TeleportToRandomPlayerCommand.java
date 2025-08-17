package com.eternalcode.core.feature.teleportrandomplayer;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import io.papermc.lib.PaperLib;
import org.bukkit.entity.Player;

@Command(name = "teleportorandomplayer", aliases = {"tprp"})
@Permission("eternalcode.tprp")
public class TeleportToRandomPlayerCommand {

    private final TeleportRandomPlayerService randomPlayerService;
    private final NoticeService noticeService;

    @Inject
    public TeleportToRandomPlayerCommand(
        TeleportRandomPlayerService randomPlayerService,
        NoticeService noticeService
    ) {
        this.randomPlayerService = randomPlayerService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Teleport to a player who hasn't been teleported to recently, ensuring fair distribution")
    void execute(@Sender Player player) {
        Player targetPlayer = this.randomPlayerService.findLeastRecentlyTeleportedPlayer(player);

        if (targetPlayer == null || !targetPlayer.isOnline()) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleportToRandomPlayer().randomPlayerNotFound())
                .send();
            return;
        }

        this.randomPlayerService.updateTeleportationHistory(player, targetPlayer);
        PaperLib.teleportAsync(player, targetPlayer.getLocation());

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.teleportToRandomPlayer().teleportedToRandomPlayer())
            .placeholder("{PLAYER}", targetPlayer.getName())
            .send();
    }

    /**
     * Teleports the player to a random player within a specific Y-level range.
     * Useful for finding players in caves, spotting potential x-rayers,
     * or quickly locating players in general.
     */
    @Execute
    @DescriptionDocs(description = "Teleport to a player who is within specified Y range and hasn't been teleported to recently")
    void executeWithYRange(@Sender Player player, @Arg int minY, @Arg int maxY) {
        if (minY > maxY) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleportToRandomPlayer().randomPlayerInRangeNotFound())
                .send();
            return;
        }

        Player targetPlayer = this.randomPlayerService.findLeastRecentlyTeleportedPlayerByY(player, minY, maxY);

        if (targetPlayer == null || !targetPlayer.isOnline()) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleportToRandomPlayer().randomPlayerInRangeNotFound())
                .send();
            return;
        }

        this.randomPlayerService.updateTeleportationHistory(player, targetPlayer);
        PaperLib.teleportAsync(player, targetPlayer.getLocation());

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.teleportToRandomPlayer().teleportedToRandomPlayerInRange())
            .placeholder("{PLAYER}", targetPlayer.getName())
            .placeholder("{Y}", String.valueOf((int) targetPlayer.getLocation().getY()))
            .send();
    }
}
