package com.eternalcode.core.command.implementation;

import com.eternalcode.core.viewer.Viewer;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.By;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;;
import dev.rollczi.litecommands.command.permission.Permission;
import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "tpos")
@Permission("eternalcore.command.tpos")
public class TposCommand {

    private final NoticeService noticeService;

    public TposCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute(min = 3, max = 4)
    public void execute(CommandSender sender, Viewer audience, @Arg Integer x, @Arg Integer y, @Arg Integer z, @Arg @By("or_sender") Player player) {
        if (sender.equals(player)) {
            this.teleport(player, x, y, z);
            return;
        }

        this.noticeService.notice()
            .message(messages -> messages.teleport().tposByMessage())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{X}", String.valueOf(x))
            .placeholder("{Y}", String.valueOf(y))
            .placeholder("{Z}", String.valueOf(z))
            .viewer(audience)
            .send();
    }


    private void teleport(Player player, int x, int y, int z) {
        Location location = new Location(player.getWorld(), x, y, z);

        PaperLib.teleportAsync(player, location);

        this.noticeService.notice()
            .message(messages -> messages.teleport().tposMessage())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{X}", String.valueOf(x))
            .placeholder("{Y}", String.valueOf(y))
            .placeholder("{Z}", String.valueOf(z))
            .player(player.getUniqueId())
            .send();
    }
}
