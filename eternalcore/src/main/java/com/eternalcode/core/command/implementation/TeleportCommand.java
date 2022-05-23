package com.eternalcode.core.command.implementation;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.argument.option.Opt;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import dev.rollczi.litecommands.command.amount.Min;
import dev.rollczi.litecommands.command.permission.Permission;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "teleport", aliases = { "tp" })
@Permission("eternalcore.teleport")
public class TeleportCommand {

    private final NoticeService noticeService;

    public TeleportCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(required = 2)
    public void to(Viewer sender, @Arg Player player, @Arg Player target) {
        PaperLib.teleportAsync(player, target.getLocation());

        this.noticeService.notice()
            .message(messages -> messages.teleport().successfullyTeleportedPlayer())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{ARG-PLAYER}", player.getName())
            .viewer(sender)
            .send();
    }

    @Execute(required = 1)
    public void execute(Player sender, Viewer senderViewer, @Arg Player player) {
        PaperLib.teleportAsync(sender, player.getLocation());

        this.noticeService.notice()
            .message(messages -> messages.teleport().successfullyTeleported())
            .placeholder("{PLAYER}", player.getName())
            .viewer(senderViewer)
            .send();
    }

    @Execute(min = 3, max = 4)
    public void to(Player sender, @Arg Location location, @Opt Option<World> world) {
        if (world.isPresent()) {
            location.setWorld(world.get());
        }

        PaperLib.teleportAsync(sender, location);
        this.noticeService.notice()
            .message(messages -> messages.teleport().tposMessage())
            .placeholder("{X}", String.valueOf(location.getX()))
            .placeholder("{Y}", String.valueOf(location.getY()))
            .placeholder("{Z}", String.valueOf(location.getZ()))
            .player(sender.getUniqueId())
            .send();
    }

    @Execute(min = 3, max = 5)
    public void to(Viewer sender, @Arg Location location, @Arg Player player, @Opt Option<World> world) {
        if (world.isPresent()) {
            location.setWorld(world.get());
        }

        PaperLib.teleportAsync(player, location);
        this.noticeService.notice()
            .message(messages -> messages.teleport().tposByMessage())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{X}", String.valueOf(location.getX()))
            .placeholder("{Y}", String.valueOf(location.getY()))
            .placeholder("{Z}", String.valueOf(location.getZ()))
            .viewer(sender)
            .send();
    }

}
