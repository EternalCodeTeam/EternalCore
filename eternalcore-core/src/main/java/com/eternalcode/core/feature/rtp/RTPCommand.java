package com.eternalcode.core.feature.rtp;

import com.eternalcode.annotations.scan.command.DescriptionDocs;
import com.eternalcode.core.notice.NoticeService;
import com.eternalcode.core.notice.NoticeTextType;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.option.Opt;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import panda.std.Option;

@Route(name = "rtp", aliases = "randomteleport")
@Permission("eternalcore.rtp")
public class RTPCommand {

    private final NoticeService noticeService;
    private final RTPService rtpService;
    private final Server server;

    public RTPCommand(NoticeService noticeService, RTPService rtpService, Server server) {
        this.noticeService = noticeService;
        this.rtpService = rtpService;
        this.server = server;
    }

    @Execute
    @DescriptionDocs(description = "Teleportation of player or sender to a random location.", arguments = "<player>")
    void rtp(Viewer sender, @Opt Option<Player> target) {
        if (target.isDefined()) {
            Player player = target.get();

            this.noticeService.create()
                .notice(translation -> translation.teleport().randomTeleportProcessBegin())
                .player(player.getUniqueId())
                .send();

            this.rtpService.randomTeleportPlayer(player);

            Location location = player.getLocation();

            this.noticeService.create()
                .notice(translation -> translation.teleport().teleportedToRandomLocation())
                .placeholder("{PLAYER}", player.getName())
                .placeholder("{WORLD}", player.getWorld().getName())
                .placeholder("{X}", String.valueOf(location.getBlockX()))
                .placeholder("{Y}", String.valueOf(location.getBlockY()))
                .placeholder("{Z}", String.valueOf(location.getBlockZ()))
                .player(player.getUniqueId())
                .send();

            this.noticeService.create()
                .notice(translation -> translation.teleport().teleportedToRandomLocationAdmin())
                .placeholder("{PLAYER}", player.getName())
                .placeholder("{WORLD}", player.getWorld().getName())
                .placeholder("{X}", String.valueOf(location.getBlockX()))
                .placeholder("{Y}", String.valueOf(location.getBlockY()))
                .placeholder("{Z}", String.valueOf(location.getBlockZ()))
                .viewer(sender)
                .send();

            return;
        }

        if (!sender.isConsole()) {
            Player player = this.server.getPlayer(sender.getUniqueId());

            this.noticeService.create()
                .notice(translation -> translation.teleport().randomTeleportProcessBegin())
                .player(player.getUniqueId())
                .send();

            this.rtpService.randomTeleportPlayer(player);

            Location location = player.getLocation();

            this.noticeService.create()
                .notice(translation -> translation.teleport().teleportedToRandomLocation())
                .placeholder("{PLAYER}", player.getName())
                .placeholder("{WORLD}", player.getWorld().getName())
                .placeholder("{X}", String.valueOf(location.getBlockX()))
                .placeholder("{Y}", String.valueOf(location.getBlockY()))
                .placeholder("{Z}", String.valueOf(location.getBlockZ()))
                .player(player.getUniqueId())
                .send();

            return;
        }

        this.noticeService.create()
            .notice(NoticeTextType.CHAT, "You must specify a player to teleport!")
            .viewer(sender)
            .send();
    }
}
