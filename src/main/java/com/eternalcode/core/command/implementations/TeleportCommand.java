package com.eternalcode.core.command.implementations;

import com.eternalcode.core.command.binds.PlayerArgument;
import com.eternalcode.core.configuration.implementations.MessagesConfiguration;
import com.eternalcode.core.utils.ChatUtils;
import dev.rollczi.litecommands.annotations.Arg;
import dev.rollczi.litecommands.annotations.Execute;
import dev.rollczi.litecommands.annotations.Handler;
import dev.rollczi.litecommands.annotations.MinArgs;
import dev.rollczi.litecommands.annotations.Permission;
import dev.rollczi.litecommands.annotations.Section;
import dev.rollczi.litecommands.annotations.UsageMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;

@Section(route = "tp", aliases = { "teleport" })
@Permission("eternalcore.command.tp")
@UsageMessage("&8» &cPoprawne użycie &7/teleport <player> [player]")
public class TeleportCommand {

    private final MessagesConfiguration messages;

    public TeleportCommand(MessagesConfiguration messages) {
        this.messages = messages;
    }

    @Execute
    @MinArgs(1)
    public void execute(CommandSender sender, @Arg(0) Player player, @Arg(1) @Handler(PlayerArgument.class) Option<Player> playerOption) {
        if (playerOption.isEmpty()) {
            if (sender instanceof Player playerSender) {
                playerSender.teleportAsync(player.getLocation());

                sender.sendMessage(ChatUtils.color(StringUtils.replace(this.messages.otherMessages.successfulyyTeleported, "{PLAYER}", player.getName())));
                return;
            }
            sender.sendMessage(ChatUtils.color(this.messages.argumentSection.onlyPlayer));
            return;
        }
        Player playerArgument = playerOption.get();

        player.teleportAsync(playerArgument.getLocation());

        sender.sendMessage(ChatUtils.color(StringUtils.replaceEach(
            this.messages.otherMessages.successfulyyTeleportedPlayer,
            new String[]{ "{PLAYER}", "{ARG-PLAYER}" },
            new String[]{ player.getName(), playerArgument.getName() })));
    }
}
