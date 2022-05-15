package com.eternalcode.core.command.implementation;

import com.eternalcode.core.chat.notification.Audience;
import com.eternalcode.core.chat.notification.NoticeService;

import dev.rollczi.litecommands.annotations.Between;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
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

    @Execute @Between(min = 3, max = 4)
    public void execute(CommandSender sender, Audience audience, @Arg(0) Integer x, @Arg(1) Integer y, @Arg(2) Integer z, @Arg(3) @Handler(PlayerArgOrSender.class) Player player) {
        if (sender.equals(player)) {
            this.teleport(player, x, y, z);
            return;
        }

        this.noticeService
            .notice()
            .message(messages -> messages.other().tposByMessage())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{X}", String.valueOf(x))
            .placeholder("{Y}", String.valueOf(y))
            .placeholder("{Z}", String.valueOf(z))
            .audience(audience)
            .send();
    }

    @IgnoreMethod
    private void teleport(Player player, int x, int y, int z) {
        Location location = new Location(player.getWorld(), x, y, z);

        PaperLib.teleportAsync(player, location);

        this.noticeService
            .notice()
            .message(messages -> messages.other().tposMessage())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{X}", String.valueOf(x))
            .placeholder("{Y}", String.valueOf(y))
            .placeholder("{Z}", String.valueOf(z))
            .player(player.getUniqueId())
            .send();
    }
}
