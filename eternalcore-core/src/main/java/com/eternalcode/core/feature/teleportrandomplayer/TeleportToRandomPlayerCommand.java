package com.eternalcode.core.feature.teleportrandomplayer;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
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
                .notice(translation -> translation.teleport().randomPlayerNotFound())
                .send();
            return;
        }

        if (targetPlayer == null) {
            this.noticeService.create()
                .player(player.getUniqueId())
                .notice(translation -> translation.teleport().randomPlayerNotFound())
                .send();
            return;
        }

        this.teleportRandomPlayerService.updateTeleportationHistory(player, targetPlayer);

        PaperLib.teleportAsync(player, targetPlayer.getLocation());

        this.noticeService.create()
            .player(player.getUniqueId())
            .notice(translation -> translation.teleport().teleportedToRandomPlayer())
            .placeholder("{PLAYER}", targetPlayer.getName())
            .send();
    }
} 
