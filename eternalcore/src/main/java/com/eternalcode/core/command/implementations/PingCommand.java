package com.eternalcode.core.command.implementations;

import com.eternalcode.core.command.argument.PlayerArgument;
import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "ping")
@Permission("eternalcore.command.ping")
@UsageMessage("&8» &cPoprawne użycie &7/kill (player)")
public final class PingCommand {

    private final MessagesConfiguration messages;

    public PingCommand(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Execute
    public void execute(Player player, @Arg(0) @Handler(PlayerArgument.class) Option<Player> playerOption) {

        if (playerOption.isEmpty()) {
            player.sendMessage(ChatUtils.color(messages.otherMessages.pingMessage.replace("{PING}", String.valueOf(player.spigot().getPing()))));
            return;
        }

        Player target = playerOption.get();

        player.sendMessage(ChatUtils.color(StringUtils.replaceEach(messages.otherMessages.pingMessage, new String[]{"{PLAYER}", "{PING}"}, new String[]{target.getName(), String.valueOf(target.spigot().getPing())})));
    }
}
