package com.eternalcode.core.command.implementations;

import com.eternalcode.core.command.argument.PlayerArgument;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.PermissionExclude;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "ping")
@PermissionExclude("eternalcore.command.ping")
@UsageMessage("&8» &cPoprawne użycie &7/ping [player]")
public class PingCommand {

    private final MessagesConfiguration message;

    public PingCommand(MessagesConfiguration message) {
        this.message = message;
    }

    @Execute
    public void execute(CommandSender sender, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player player) {
                sender.sendMessage(ChatUtils.color(message.otherMessages.pingMessage.replace("{PING}", String.valueOf(player.spigot().getPing()))));
                return;
            }

            sender.sendMessage(ChatUtils.color(this.message.argumentSection.onlyPlayer));
            return;
        }

        Player player = playerOption.get();

        sender.sendMessage(ChatUtils.color(StringUtils.replaceEach(message.otherMessages.pingOtherMessage, new String[]{ "{PLAYER}", "{PING}" }, new String[]{ player.getName(), String.valueOf(player.getPing()) })));
    }
}
