package com.eternalcode.core.feature.teleportrandomplayer;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import io.papermc.lib.PaperLib;
import org.bukkit.entity.Player;

@Command(name = "teleportorandomplayer", aliases = {"tprp"})
@Permission("eternalcode.tprp")
public class TeleportToRandomPlayerCommand {

    private final TeleportRandomPlayerService teleportRandomPlayerService;
    private final NoticeService noticeService;

    @Inject
    public TeleportToRandomPlayerCommand(
        TeleportRandomPlayerService teleportRandomPlayerService,
        NoticeService noticeService
    ) {
        this.teleportRandomPlayerService = teleportRandomPlayerService;
        this.noticeService = noticeService;
    }

    @Execute
    @DescriptionDocs(description = "Teleport to a player who hasn't been teleported to recently, ensuring fair distribution")
    void execute(@Context Player player) {
        Player targetPlayer = this.teleportRandomPlayerService.findLeastRecentlyTeleportedPlayer(player);

        if (targetPlayer != null && targetPlayer.equals(player)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleportToRandomPlayer().randomPlayerNotFound())
                .send();
            return;
        }

        if (targetPlayer == null) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleportToRandomPlayer().randomPlayerNotFound())
                .send();
            return;
        }

        this.teleportRandomPlayerService.updateTeleportationHistory(player, targetPlayer);

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
    void executeWithYRange(@Context Player player, @Arg int minY, @Arg int maxY) {
        Player targetPlayer = this.teleportRandomPlayerService.findLeastRecentlyTeleportedPlayerByY(player, minY, maxY);

        if (targetPlayer != null && targetPlayer.equals(player)) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleportToRandomPlayer().randomPlayerInRangeNotFound())
                .send();
            return;
        }

        if (targetPlayer == null) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleportToRandomPlayer().randomPlayerInRangeNotFound())
                .send();
            return;
        }

        this.teleportRandomPlayerService.updateTeleportationHistory(player, targetPlayer);

        PaperLib.teleportAsync(player, targetPlayer.getLocation());

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.teleportToRandomPlayer().teleportedToRandomPlayerInRange())
            .placeholder("{PLAYER}", targetPlayer.getName())
            .placeholder("{Y}", String.valueOf((int) targetPlayer.getLocation().getY()))
            .send();
    }
}
