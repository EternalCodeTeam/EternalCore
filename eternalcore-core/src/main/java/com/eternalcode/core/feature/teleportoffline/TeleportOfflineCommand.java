package com.eternalcode.core.feature.teleportoffline;

import com.eternalcode.core.feature.teleport.TeleportService;
import com.eternalcode.core.injector.annotations.Inject;
import com.eternalcode.core.notice.NoticeService;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Sender;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@Command(name = "tpoffline", aliases = "tpo")
@Permission("eternalcore.teleportoffline")
class TeleportOfflineCommand {

    private final NoticeService noticeService;
    private final TeleportService teleportService;

    @Inject
    TeleportOfflineCommand(NoticeService noticeService, TeleportService teleportService) {
        this.noticeService = noticeService;
        this.teleportService = teleportService;
    }

    @Execute
    void execute(@Sender Player sender, @Arg OfflinePlayer target) {
        if (!target.hasPlayedBefore()) {
            this.noticeService.create()
                .player(sender.getUniqueId())
                .placeholder("{PLAYER}", target.getName())
                .notice(translation -> translation.teleportToOfflinePlayer().offlinePlayerNotPlayedBefore())
                .send();
            return;
        }

        Location location = target.getLocation();
        if (location == null) {
            this.noticeService.create()
                .player(sender.getUniqueId())
                .notice(translation -> translation.teleportToOfflinePlayer().lastLocationNotFound())
                .send();
            return;
        }

        this.teleportService.teleport(sender, location);
        this.noticeService.create()
            .player(sender.getUniqueId())
            .placeholder("{PLAYER}", target.getName())
            .notice(translation -> translation.teleportToOfflinePlayer().teleportedToPlayerLastLocation())
            .send();
    }

}
