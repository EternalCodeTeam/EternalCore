package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.NoticeService;
import com.eternalcode.core.command.argument.PlayerArgOrSender;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Between;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.IgnoreMethod;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Section(route = "tpos")
@Permission("eternalcore.command.tpos")
@UsageMessage("&8» &cPoprawne użycie &7/tpos <x> <y> <z> [gracz]")
public class TposCommand {

    private final NoticeService noticeService;

    public TposCommand(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Execute @Between(min = 3, max = 4)
    public void execute(CommandSender sender,
                        @Arg(0) Integer x,
                        @Arg(1) Integer y,
                        @Arg(2) Integer z,
                        @Arg(3) @Handler(PlayerArgOrSender.class) Player player
    ) {
        this.teleport(player, x, y, z);

        if (sender.equals(player)) {
            return;
        }

        this.noticeService
            .notice()
            .message(messages -> messages.other().tposByMessage())
            .placeholder("{PLAYER}", player.getName())
            .placeholder("{X}", String.valueOf(x))
            .placeholder("{Y}", String.valueOf(y))
            .placeholder("{Z}", String.valueOf(z))
            .sender(sender)
            .send();
    }

    @IgnoreMethod
    private void teleport(Player player, int x, int y, int z) {
        Location location = new Location(player.getWorld(), x, y, z);

        player.teleportAsync(location);

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
