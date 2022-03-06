package com.eternalcode.core.command.implementations;

import com.eternalcode.core.chat.notification.AudiencesService;
import com.eternalcode.core.command.argument.PlayerArgument;
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
import panda.std.Option;

@Section(route = "tpos")
@Permission("eternalcore.command.tpos")
@UsageMessage("&8» &cPoprawne użycie &7/tpos <x> <y> <z> [gracz]")
public class TposCommand {

    private final AudiencesService audiencesService;

    public TposCommand(AudiencesService audiencesService) {
        this.audiencesService = audiencesService;
    }

    @Execute @Between(min = 3, max = 4)
    public void execute(CommandSender sender, @Arg(0) Integer x, @Arg(1) Integer y, @Arg(2) Integer z, @Arg(3) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                teleport(player, x, y, z);
                return;
            }

            this.audiencesService.console(messages -> messages.argument().onlyPlayer());
            return;
        }
        Player player = playerOption.get();

        teleport(player, x, y, z);

        this.audiencesService
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

        this.audiencesService
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
