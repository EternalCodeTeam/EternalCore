package com.eternalcode.core.command.implementations;

import com.eternalcode.core.command.argument.PlayerArgument;
import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
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
import panda.utilities.text.Formatter;

@Section(route = "tpos")
@Permission("eternalcore.command.tpos")
@UsageMessage("&8» &cPoprawne użycie &7/tpos <x> <y> <z> [gracz]")
public class TposCommand {

    private final MessagesConfiguration messages;

    public TposCommand(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Execute @Between(min = 3, max = 4)
    public void execute(CommandSender sender, @Arg(0) Integer x, @Arg(1) Integer y, @Arg(2) Integer z, @Arg(3) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                teleport(player, x, y, z);
                return;
            }
            sender.sendMessage(ChatUtils.color(this.messages.argumentSection.onlyPlayer));
            return;
        }
        Player player = playerOption.get();

        teleport(player, x, y, z);

        Formatter formatter = new Formatter()
            .register("{PLAYER}", player.getName())
            .register("{X}", x)
            .register("{Y}", y)
            .register("{Z}", z);

        sender.sendMessage(ChatUtils.color(formatter.format(this.messages.otherMessages.tposByMessage)));
    }

    @IgnoreMethod
    private void teleport(Player player, int x, int y, int z) {
        Location location = new Location(player.getWorld(), x, y, z);

        player.teleportAsync(location);

        Formatter formatter = new Formatter()
            .register("{X}", location.getBlockX())
            .register("{Y}", location.getBlockY())
            .register("{Z}", location.getBlockZ());

        player.sendMessage(ChatUtils.color(formatter.format(this.messages.otherMessages.tposMessage)));
    }
}
