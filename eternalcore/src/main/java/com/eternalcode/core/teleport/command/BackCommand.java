package com.eternalcode.core.teleport.command;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.teleport.TeleportService;
import com.eternalcode.core.viewer.Viewer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "back")
public class BackCommand {

    private final TeleportService teleportService;
    private final NoticeService noticeService;

    public BackCommand(TeleportService teleportService, NoticeService noticeService) {
        this.teleportService = teleportService;
        this.noticeService = noticeService;
    }

    @Execute(required = 0)
    public void execute(Player player) {
        Option<Location> location = this.teleportService.getLastLocation(player.getUniqueId());

        if (location.isEmpty()) {
            this.noticeService.player(player.getUniqueId(), messages -> messages.teleport().lastLocationNoExist());
            return;
        }

        this.teleportService.teleport(player, location.get());
        this.noticeService.player(player.getUniqueId(), messages -> messages.teleport().teleportedToLastLocation());
    }

    @Execute(required = 1)
    public void execute(Viewer viewer, @Arg Player player) {
        Option<Location> location = this.teleportService.getLastLocation(player.getUniqueId());

        if (location.isEmpty()) {
            this.noticeService.viewer(viewer, messages -> messages.teleport().lastLocationNoExist());
            return;
        }

        this.teleportService.teleport(player, location.get());
        this.noticeService.player(player.getUniqueId(), messages -> messages.teleport().teleportedToLastLocation());
        this.noticeService.create()
            .viewer(viewer)
            .message(messages -> messages.teleport().teleportedSpecifiedPlayerLastLocation())
            .placeholder("{PLAYER}", player.getName())
            .send();
    }

}
